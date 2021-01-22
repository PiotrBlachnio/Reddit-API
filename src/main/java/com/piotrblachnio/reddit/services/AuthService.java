package com.piotrblachnio.reddit.services;

import com.piotrblachnio.reddit.dto.response.AuthenticationResponse;
import com.piotrblachnio.reddit.dto.request.*;
import com.piotrblachnio.reddit.exceptions.*;
import com.piotrblachnio.reddit.mapper.UserMapper;
import com.piotrblachnio.reddit.models.*;
import com.piotrblachnio.reddit.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final EventService eventService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserMapper userMapper;

    @Transactional
    public void register(RegisterRequest registerRequest) {
        userRepository.findByEmail(registerRequest.getEmail()).ifPresent(value -> {
            throw new DuplicateEmailException();
        });

        userRepository.findByUsername(registerRequest.getUsername()).ifPresent(value -> {
            throw new DuplicateUsernameException();
        });

        var user = userMapper.mapRegisterRequestToUser(registerRequest);

        userRepository.save(user);

        eventService.sendConfirmationMail(user);
    }

    @Transactional
    public AuthenticationResponse login(LoginRequest loginRequest) {
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var accessToken = jwtService.generateToken(authentication);
        var refreshToken = refreshTokenService.generateRefreshToken().getToken();

        return new AuthenticationResponse(accessToken, refreshToken);
    }

    public void confirmEmail(String token) {
        var verificationToken = verificationTokenRepository.findByToken(token).orElseThrow(() -> new InvalidConfirmationTokenException());

        var isExpired = Instant.now().isAfter(verificationToken.getExpiresAt());
        if(isExpired) throw new ExpiredTokenException();

        var email = verificationToken.getUser().getEmail();
        var user = userRepository.findByEmail(email).orElseThrow(() -> new EmailNotFoundException());

        user.setConfirmed(true);
        userRepository.save(user);

        verificationTokenRepository.deleteById(verificationToken.getId());
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        var principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(principal.getUsername()).orElseThrow(() -> new EmailNotFoundException());
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());

        var accessToken = jwtService.generateTokenWithUserName(refreshTokenRequest.getEmail());
        var refreshToken = refreshTokenRequest.getRefreshToken();

        return new AuthenticationResponse(accessToken, refreshToken);
    }

    public boolean isLoggedIn() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
