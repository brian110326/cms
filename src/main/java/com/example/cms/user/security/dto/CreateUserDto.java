package com.example.cms.user.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "회원 생성 요청 DTO")
public class CreateUserDto {

    @NotNull
    @Schema(description = "사용자 아이디", example = "testUser")
    private String username;

    @NotNull
    @Schema(description = "사용자 비밀번호", example = "password123!")
    private String password;

}
