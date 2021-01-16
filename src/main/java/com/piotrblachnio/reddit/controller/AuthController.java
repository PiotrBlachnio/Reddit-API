package com.piotrblachnio.reddit.controller;

import com.piotrblachnio.reddit.constants.ApiRoutes;
import com.piotrblachnio.reddit.dto.*;
import com.piotrblachnio.reddit.dto.request.*;
import com.piotrblachnio.reddit.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

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
    public ResponseEntity<String> logout(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted Successfully!!");
    }

    @PostMapping(ApiRoutes.Auth.REFRESH_TOKEN)
    public AuthenticationResponse refreshTokens(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @GetMapping(ApiRoutes.Auth.CONFIRM_EMAIL)
    public ResponseEntity<String> confirmEmail(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity("Account verified successfully", HttpStatus.OK);
    }
}
