package com.piotrblachnio.reddit.mapper;

import com.piotrblachnio.reddit.dto.request.RegisterRequest;
import com.piotrblachnio.reddit.model.User;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mapping(target = "username", source = "registerRequest.username")
    @Mapping(target = "email", source = "registerRequest.email")
    @Mapping(target = "password", expression = "java(hashPassword(registerRequest.getPassword()))")
    public abstract User mapRegisterRequestToUser(RegisterRequest registerRequest);

    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }
}