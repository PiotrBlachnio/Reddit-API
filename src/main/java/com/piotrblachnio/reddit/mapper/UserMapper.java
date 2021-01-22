package com.piotrblachnio.reddit.mapper;

import com.piotrblachnio.reddit.dto.request.RegisterRequest;
import com.piotrblachnio.reddit.models.User;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mapping(target = "password", expression = "java(hashPassword(registerRequest))")
    public abstract User mapRegisterRequestToUser(RegisterRequest registerRequest);

    public String hashPassword(RegisterRequest registerRequest) {
        return passwordEncoder.encode(registerRequest.getPassword());
    }
}