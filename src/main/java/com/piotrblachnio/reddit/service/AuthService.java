package com.piotrblachnio.reddit.service;

import com.piotrblachnio.reddit.dto.response.AuthenticationResponse;
import com.piotrblachnio.reddit.dto.request.*;
import com.piotrblachnio.reddit.exceptions.*;
import com.piotrblachnio.reddit.mapper.UserMapper;
import com.piotrblachnio.reddit.model.*;
import com.piotrblachnio.reddit.repository.*;
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
        var verificationToken = verificationTokenRepository.findByToken(token).orElseThrow(() -> new SpringRedditException("Provided token is invalid"));

        var isExpired = Instant.now().isAfter(verificationToken.getExpiresAt());
        if(isExpired) throw new SpringRedditException("Provided token has already expired");

        var username = verificationToken.getUser().getUsername();
        var user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User associated with the provided token does not exist"));

        user.setConfirmed(true);
        userRepository.save(user);

        verificationTokenRepository.deleteById(verificationToken.getId());
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        var principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());

        var accessToken = jwtService.generateTokenWithUserName(refreshTokenRequest.getUsername());
        var refreshToken = refreshTokenRequest.getRefreshToken();

        return new AuthenticationResponse(accessToken, refreshToken);
    }

    public boolean isLoggedIn() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
