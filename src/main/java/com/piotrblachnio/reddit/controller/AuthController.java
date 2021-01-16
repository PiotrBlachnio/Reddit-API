package com.piotrblachnio.reddit.controller;

import com.piotrblachnio.reddit.constants.ApiRoutes;
import com.piotrblachnio.reddit.dto.AuthenticationResponse;
import com.piotrblachnio.reddit.dto.LoginRequest;
import com.piotrblachnio.reddit.dto.RefreshTokenRequest;
import com.piotrblachnio.reddit.dto.RegisterRequest;
import com.piotrblachnio.reddit.service.AuthService;
import com.piotrblachnio.reddit.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping(ApiRoutes.Auth.REGISTER)
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping(ApiRoutes.Auth.LOGIN)
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping(ApiRoutes.Auth.LOGOUT)
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted Successfully!!");
    }

    @PostMapping(ApiRoutes.Auth.REFRESH_TOKEN)
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @GetMapping(ApiRoutes.Auth.CONFIRM_EMAIL)
    public ResponseEntity<String> confirmEmail(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity("Account verified successfully", HttpStatus.OK);
    }
}
