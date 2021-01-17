package com.piotrblachnio.reddit.model;

import lombok.*;
import javax.persistence.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;

    private String password;

    private String email;

    private Instant created = Instant.now();

    private boolean enabled = false;
}