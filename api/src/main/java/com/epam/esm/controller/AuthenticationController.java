package com.epam.esm.controller;

import com.epam.esm.jwt.JWTProvider;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.user.UserPostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final UserService userService;
    private final JWTProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(UserService userService, JWTProvider jwtProvider, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.OK)
    public String authorizeUser(@RequestBody UserPostDTO userCredentialDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userCredentialDto.getUsername(), userCredentialDto.getPassword()));
        String token = jwtProvider.generateToken(userCredentialDto.getUsername());
        return token;
    }

}
