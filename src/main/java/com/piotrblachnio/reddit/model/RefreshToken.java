package com.piotrblachnio.reddit.model;

import lombok.*;
import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private Instant createdDate = Instant.now();

    public RefreshToken(String token) {
        this.token = token;
    }
}