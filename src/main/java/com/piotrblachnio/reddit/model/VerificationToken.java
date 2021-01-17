package com.piotrblachnio.reddit.model;

import lombok.*;
import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "token")
public class VerificationToken {
    private static final Integer MINUTES_30 = 1800000;

    public VerificationToken(User user) {
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    private String token = UUID.randomUUID().toString();

    private Instant expiresAt = Instant.now().plusMillis(MINUTES_30);
}