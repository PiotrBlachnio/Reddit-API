package com.piotrblachnio.reddit.services;

import com.piotrblachnio.reddit.exceptions.InvalidCredentialsException;
import com.piotrblachnio.reddit.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.User;
import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@Service()
public class SecurityService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new InvalidCredentialsException());
        return new User(user.getEmail(), user.getPassword(), user.isConfirmed(), true, true, true, getAuthorities("USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}