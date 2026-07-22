package com.mini.inventory.service;

import com.mini.inventory.dto.auth.LoginRequest;
import com.mini.inventory.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public Authentication login(LoginRequest request) {

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword());

        return authenticationManager.authenticate(authentication);

    }

}