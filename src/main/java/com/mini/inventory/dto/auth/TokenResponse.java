package com.mini.inventory.dto.auth;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {

    private String accessToken;

    private String refreshToken;

    private String tokenType;
}
