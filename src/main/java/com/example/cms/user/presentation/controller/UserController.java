package com.example.cms.user.presentation.controller;

import com.example.cms.user.application.service.UserService;
import com.example.cms.user.security.dto.CreateUserDto;
import com.example.cms.user.security.dto.LoginRequestDto;
import com.example.cms.user.security.dto.LoginResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "User-Controller", description = "회원가입, 로그인 관련 API")
public class UserController {

    private final UserService userService;

    // -------------------- 회원가입 --------------------
    @PostMapping("/signup")
    @Operation(summary = "회원가입 API",
            description = "아이디와 비밀번호를 통해 회원가입을 할 수 있습니다")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "회원 생성 성공",
                    content = @Content(schema = @Schema(implementation = Void.class))
            )
    })
    public ResponseEntity<Void> createUser(
            @Parameter(description = "회원가입 요청 DTO", required = true,
                    examples = @ExampleObject(value = "{ \"username\": \"testuser\", \"password\": \"test1234\" }"))
            @Valid @RequestBody CreateUserDto dto
    ) {
        userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // -------------------- 로그인 --------------------
    @PostMapping("/login")
    @Operation(summary = "로그인 API",
            description = "아이디와 비밀번호를 통해 로그인을 할 수 있으며, Authorization 헤더에 액세스 토큰을 반환합니다")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "회원 로그인 성공",
                    content = @Content(
                            schema = @Schema(implementation = LoginResponseDto.class),
                            examples = @ExampleObject(value = "{ \"accessToken\": \"Bearer abc.def.ghi\", \"userId\": 1 }")
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = Void.class))
            )
    })
    public ResponseEntity<LoginResponseDto> loginUser(
            @Parameter(description = "로그인 요청 DTO", required = true,
                    examples = @ExampleObject(value = "{ \"username\": \"testuser\", \"password\": \"test1234\" }"))
            @Valid @RequestBody LoginRequestDto dto,
            HttpServletResponse response
    ) {
        LoginResponseDto responseDto = userService.loginUser(dto);
        response.setHeader("Authorization", responseDto.accessToken());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}