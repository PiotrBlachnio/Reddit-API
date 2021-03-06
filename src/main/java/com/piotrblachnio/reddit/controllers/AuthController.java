package com.piotrblachnio.reddit.controllers;

import com.piotrblachnio.reddit.constants.ApiRoutes;
import com.piotrblachnio.reddit.dto.request.*;
import com.piotrblachnio.reddit.dto.response.AuthenticationResponse;
import com.piotrblachnio.reddit.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping(ApiRoutes.Auth.REGISTER)
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
    }

    @PostMapping(ApiRoutes.Auth.LOGIN)
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping(ApiRoutes.Auth.LOGOUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@Valid @RequestBody LogoutRequest logoutRequest) {
        refreshTokenService.deleteRefreshToken(logoutRequest.getRefreshToken());
    }

    @PostMapping(ApiRoutes.Auth.REFRESH_TOKEN)
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @GetMapping(ApiRoutes.Auth.CONFIRM_EMAIL)
    @ResponseStatus(HttpStatus.OK)
    public void confirmEmail(@PathVariable String token) {
        authService.confirmEmail(token);
    }
}