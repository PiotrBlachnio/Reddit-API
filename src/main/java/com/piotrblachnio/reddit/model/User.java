package com.piotrblachnio.reddit.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message = "Username cannot be empty")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    @Email
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    private Instant created;

    private boolean enabled;
}
