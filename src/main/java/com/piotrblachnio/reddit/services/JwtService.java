package com.piotrblachnio.reddit.services;

import com.piotrblachnio.reddit.constants.Jwt;
import com.piotrblachnio.reddit.exceptions.SpringRedditException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.security.*;
import java.time.Instant;
import java.util.Date;

import static io.jsonwebtoken.Jwts.parser;

@Service
public class JwtService {
    private KeyStore keyStore;

    @PostConstruct
    public void init() {
        try {
            this._loadKeystore();
        } catch(Exception exception) {
            throw new RuntimeException("Exception occurred while loading key store");
        }
    }

    public String generateToken(Authentication authentication) {
        var principal = (User) authentication.getPrincipal();
        return generateTokenWithUserName(principal.getUsername());
    }

    public String generateTokenWithUserName(String username) {
        return Jwts.builder()
                .setSubject(username)
                .signWith(_getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(Jwt.EXPIRES_IN)))
                .compact();
    }

    public boolean validateToken(String jwt) {
        parser().setSigningKey(_getPublicKey()).parseClaimsJws(jwt);
        return true;
    }

    public String getEmailFromJwt(String token) {
        var claims = parser()
                .setSigningKey(_getPublicKey())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    private PublicKey _getPublicKey() {
        try {
            return keyStore.getCertificate(Jwt.KEY).getPublicKey();
        } catch (Exception e) {
            throw new SpringRedditException("Exception occurred while retrieving key from key store");
        }
    }

    private PrivateKey _getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey(Jwt.KEY, Jwt.JKS_PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred when retrieving key from key store");
        }
    }

    private void _loadKeystore() throws Exception {
        keyStore = KeyStore.getInstance("JKS");

        var resourceAsStream = getClass().getResourceAsStream(Jwt.JKS);

        keyStore.load(resourceAsStream, Jwt.JKS_PASSWORD);
    }
}