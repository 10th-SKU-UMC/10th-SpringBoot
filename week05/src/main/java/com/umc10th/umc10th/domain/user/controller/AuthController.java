package com.umc10th.umc10th.domain.user.controller;

import com.umc10th.umc10th.domain.user.dto.UserReqDto;
import com.umc10th.umc10th.domain.user.dto.UserResDto;
import com.umc10th.umc10th.domain.user.exception.code.BaseSuccessCode;
import com.umc10th.umc10th.domain.user.service.UserService;
import com.umc10th.umc10th.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/users")
public class AuthController {

    private final UserService userService;


    @PostMapping("/signup")
    public ApiResponse<UserResDto.SignUpResponseDto> signup(@RequestBody UserReqDto.SignUpRequestDto dto) {

        return ApiResponse.onSuccess(BaseSuccessCode.OK, null);
    }

    @PostMapping("/login")
    public ApiResponse<UserResDto.LoginResultDto> login(
            @RequestBody @Valid UserReqDto.loginRequestDto request) {
        return ApiResponse.onSuccess(BaseSuccessCode.OK, null);
    }
}
