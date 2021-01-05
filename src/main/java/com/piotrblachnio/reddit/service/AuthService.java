package com.piotrblachnio.reddit.service;

import com.piotrblachnio.reddit.dto.LoginRequest;
import com.piotrblachnio.reddit.dto.RegisterRequest;
import com.piotrblachnio.reddit.exceptions.SpringRedditException;
import com.piotrblachnio.reddit.model.*;
import com.piotrblachnio.reddit.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();

        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        var token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail
        ("Please activate your account", user.getEmail(),
                "http://localhost:8080/api/auth/accountVerification/" + token));
    }

    private String generateVerificationToken(User user) {
        var token = UUID.randomUUID().toString();
        var verificationToken = new VerificationToken();

        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        var verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new SpringRedditException("Invalid token"));
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        var username = verificationToken.getUser().getUsername();
        var user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User with this username not found"));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    }
}