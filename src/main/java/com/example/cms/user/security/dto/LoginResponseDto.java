package com.example.cms.user.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 응답 DTO")
public record LoginResponseDto(
        @Schema(description = "사용자 액세스 토큰", example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String accessToken,

        @Schema(description = "사용자 고유 ID", example = "1")
        Long userId
) {

    public static LoginResponseDto of(String accessToken, Long userId) {
        return new LoginResponseDto(accessToken, userId);
    }
}
