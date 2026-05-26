package com.example.umc10th.member.controller;

import com.example.umc10th.global.response.ApiResponse;
import com.example.umc10th.member.dto.MemberReqDTO;
import com.example.umc10th.member.dto.MemberResDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping("/auth/signup")
    public ApiResponse<MemberResDTO.SignUpResponse> signUp(@RequestBody @Valid MemberReqDTO.SignUpRequest request) {
        MemberResDTO.SignUpResponse response = new MemberResDTO.SignUpResponse(
                1L,
                request.name(),
                request.phoneNum(),
                request.nickName()
        );

        return ApiResponse.onSuccess("MEMBER201", "회원가입 성공", response);
    }
}

