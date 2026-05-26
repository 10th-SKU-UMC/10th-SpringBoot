package com.example.umc10th.member.controller;

import com.example.umc10th.global.response.ApiResponse;
import com.example.umc10th.member.dto.MemberReqDTO;
import com.example.umc10th.member.dto.MemberResDTO;
import com.example.umc10th.member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final MemberService memberService;

    public AuthController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/auth/signup")
    public ApiResponse<MemberResDTO.SignUpResponse> signUp(@RequestBody @Valid MemberReqDTO.SignUpRequest request) {
        MemberResDTO.SignUpResponse response = memberService.signUp(request);
        return ApiResponse.onSuccess("MEMBER201", "회원가입 성공", response);
    }

    @PostMapping("/auth/login")
    public ApiResponse<MemberResDTO.LoginResponse> login(@RequestBody @Valid MemberReqDTO.LoginRequest request) {
        MemberResDTO.LoginResponse response = memberService.login(request);
        return ApiResponse.onSuccess("MEMBER200_2", "로그인 성공", response);
    }
}
