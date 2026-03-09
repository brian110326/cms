package com.example.cms.user.security.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateUserDto {

    @NotNull
    private String username;
    @NotNull
    private String password;

}
