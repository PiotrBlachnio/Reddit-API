package com.piotrblachnio.reddit.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String email;

    private String username;

    private String password;
}