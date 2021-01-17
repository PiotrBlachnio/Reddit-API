package com.piotrblachnio.reddit.service;

import com.piotrblachnio.reddit.dto.response.AuthenticationResponse;
import com.piotrblachnio.reddit.dto.request.*;
import com.piotrblachnio.reddit.exceptions.SpringRedditException;
import com.piotrblachnio.reddit.mapper.UserMapper;
import com.piotrblachnio.reddit.model.*;
import com.piotrblachnio.reddit.repository.*;
import com.piotrblachnio.reddit.security.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserMapper userMapper;

    @Transactional
    public void register(RegisterRequest registerRequest) {
        var user = userMapper.mapRegisterRequestToUser(registerRequest);

        userRepository.save(user);

        var token = _generateVerificationToken(user);

        mailService.sendMail(new NotificationEmail("Please activate your account", user.getEmail(),"http://localhost:8080/api/v1/auth/confirm-email/" + token));
    }

    public void verifyAccount(String token) {
        var verificationToken = verificationTokenRepository.findByToken(token);
        fetchUserAndEnable(verificationToken.orElseThrow(() -> new SpringRedditException("Invalid token")));
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        var principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    @Transactional
    public AuthenticationResponse login(LoginRequest loginRequest) {
        var authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        var token = jwtProvider.generateToken(authenticate);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    public boolean isLoggedIn() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

    private String _generateVerificationToken(User user) {
        var token = UUID.randomUUID().toString();

        var verificationToken = new VerificationToken(user);

        verificationTokenRepository.save(verificationToken);

        return token;
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        var username = verificationToken.getUser().getUsername();
        var user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User with this username not found"));
        user.setEnabled(true);
        userRepository.save(user);
    }
}
