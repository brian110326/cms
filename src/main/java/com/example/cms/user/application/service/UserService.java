package com.example.cms.user.application.service;

import com.example.cms.user.domain.entity.User;
import com.example.cms.user.domain.entity.UserRoleEnum;
import com.example.cms.user.infrastructure.repository.UserRepository;
import com.example.cms.user.security.dto.CreateUserDto;
import com.example.cms.user.security.dto.LoginRequestDto;
import com.example.cms.user.security.dto.LoginResponseDto;
import com.example.cms.user.security.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void createUser(CreateUserDto dto){
        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(UserRoleEnum.USER)
                .build();

        userRepository.save(user);
    }

    public LoginResponseDto loginUser(LoginRequestDto dto){
        User user = userRepository.findByUsername(dto.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("해당 User를 찾을 수 없습니다."));

        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())){
            throw new BadCredentialsException("비밀번호가 올바르지 않습니다.");
        }

        String accessToken = jwtUtil.createAccessToken(dto.getUsername(), user.getRole());

        return new LoginResponseDto(accessToken, user.getId());
    }

}
