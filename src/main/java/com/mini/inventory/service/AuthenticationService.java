package com.mini.inventory.service;

import com.mini.inventory.dto.auth.LoginRequest;
import com.mini.inventory.dto.auth.RefreshTokenRequest;
import com.mini.inventory.dto.auth.TokenResponse;
import com.mini.inventory.entity.RefreshToken;
import com.mini.inventory.entity.User;
import com.mini.inventory.security.JwtService;
import com.mini.inventory.security.RefreshTokenService;
import com.mini.inventory.security.UserPrincipal;
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
    private final RefreshTokenService refreshTokenService;

    public TokenResponse login(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()));

        UserPrincipal principal =
                (UserPrincipal) authentication.getPrincipal();

        String accessToken =  jwtService.generateToken(principal);

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(
                        principal.getUser()
                );

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .build();
    }

    public TokenResponse refreshToken(
            RefreshTokenRequest request) {
        RefreshToken refreshToken =
                refreshTokenService.verifyRefreshToken(
                        request.getRefreshToken());

        User user = refreshToken.getUser();
        String accessToken =
                jwtService.generateToken(
                        new UserPrincipal(user));

        RefreshToken newRefreshToken =
                refreshTokenService.createRefreshToken(user);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken.getToken())
                .tokenType("Bearer")
                .build();
    }

}