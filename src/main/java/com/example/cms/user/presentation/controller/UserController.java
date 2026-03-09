package com.example.cms.user.presentation.controller;

import com.example.cms.user.application.service.UserService;
import com.example.cms.user.security.dto.CreateUserDto;
import com.example.cms.user.security.dto.LoginRequestDto;
import com.example.cms.user.security.dto.LoginResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> createUser(@Valid @RequestBody CreateUserDto dto){
        userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> loginUser(@Valid @RequestBody LoginRequestDto dto,
                                          HttpServletResponse response){
        LoginResponseDto responseDto = userService.loginUser(dto);
        response.setHeader("Authorization", responseDto.accessToken());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
