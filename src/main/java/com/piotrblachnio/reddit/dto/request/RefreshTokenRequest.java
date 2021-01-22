package com.piotrblachnio.reddit.dto.request;

import lombok.*;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequest {
    @NotEmpty(message = "Refresh token cannot be empty")
    private String refreshToken;

    @Email(message = "Email should be valid")
    @Size(min = 5, max = 64, message = "Email should be between 5 and 64 characters")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
}