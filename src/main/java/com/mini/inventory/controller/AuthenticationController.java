package com.mini.inventory.controller;

import com.mini.inventory.dto.auth.LoginRequest;
import com.mini.inventory.dto.auth.LoginResponse;
import com.mini.inventory.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

       String token =  authenticationService.login(request);

//        return ResponseEntity.ok(
//                new LoginResponse("Login Successful")

        return ResponseEntity.ok(
                new LoginResponse(token)
        );
    }

}