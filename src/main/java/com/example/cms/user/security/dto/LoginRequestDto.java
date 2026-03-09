package com.example.cms.user.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "로그인 요청 DTO")
public class LoginRequestDto {

    @NotNull
    @Schema(description = "사용자 이름", example = "user123", required = true)
    private String username;

    @NotNull
    @Schema(description = "사용자 비밀번호", example = "password123", required = true)
    private String password;
}