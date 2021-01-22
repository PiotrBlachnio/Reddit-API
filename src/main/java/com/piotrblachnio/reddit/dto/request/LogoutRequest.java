package com.piotrblachnio.reddit.dto.request;

import lombok.*;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogoutRequest {
    @NotEmpty(message = "Refresh token cannot be empty")
    private String refreshToken;
}