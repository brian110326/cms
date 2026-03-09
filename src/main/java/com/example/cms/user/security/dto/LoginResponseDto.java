package com.example.cms.user.security.dto;

public record LoginResponseDto(String accessToken, Long userId) {

    public static LoginResponseDto of(String accessToken, Long userId) {
        return new LoginResponseDto(accessToken, userId);
    }

}
