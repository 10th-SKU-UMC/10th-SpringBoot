package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.MemberException;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import com.example.umc10th.global.security.entity.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")

public class MemberController {

    private final MemberService memberService;

    // 아무것도 받지 않을 경우
    @GetMapping("/test")
    public String test(){
        throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
    }

    // Query Parameter
    @PostMapping("/query-paramter")
    public String exception(
            @RequestParam String queryParameter
    ){
        return memberService.singleParameter(queryParameter);
    }

    // Request Body
    @PostMapping("/request-body")
    public MemberResDTO.RequestBody requestBody(
            @RequestBody MemberReqDTO.RequestBody dto
    ){
        return memberService.requestBody(dto);
    }

    // Path Variable
    @PostMapping("/{pathVariable}")
    public String pathVariable(
            @PathVariable String pathVariable
    ){
        return memberService.singleParameter(pathVariable);
    }

    // Header
    @PostMapping("/header")
    public String header(
            @RequestHeader("test") String test
    ){
        return memberService.singleParameter(test);
    }

    /*// 마이페이지
    @PostMapping("/v1/users/me")
    public ApiResponse<MemberResDTO.GetInfo> getInfo(
            @RequestBody MemberReqDTO.GetInfo dto
    ){
        BaseSuccessCode code = MemberSuccessCode.OK;
        return ApiResponse.onSuccess(code, memberService.getInfo(dto));
    }*/
    // 인증 마이페이지
    @PostMapping("/v2/users/me")
    public ApiResponse<MemberResDTO.GetInfo> getInfo(
            @AuthenticationPrincipal AuthMember member
    ){
        BaseSuccessCode code = MemberSuccessCode.OK;
        return ApiResponse.onSuccess(code, memberService.getInfo(member));
    }

    // 인증 마이페이지 week09
    @PostMapping("/v3/users/me")
    public ApiResponse<MemberResDTO.MyPageResDTO> getMyPage(
            @AuthenticationPrincipal AuthMember authMember
    ){
        MemberResDTO.MyPageResDTO result = MemberConverter.toMyPageResDTO(authMember.getMember());
        BaseSuccessCode code = MemberSuccessCode.OK;
        return ApiResponse.onSuccess(code, result);
    }

    // 로그인
    @PostMapping("/v1/auth/users/login")
    public ApiResponse<MemberResDTO.LoginResDTO> login(
            @RequestBody MemberReqDTO.LoginReqDTO request
    ){
        MemberResDTO.LoginResDTO result = memberService.login(request);
        return ApiResponse.onSuccess(MemberSuccessCode.OK, result);
    }

    // 회원 가입
    @PostMapping("/v1/auth/register")
    public ApiResponse<MemberResDTO.RegisterResDTO> register(
            @RequestBody MemberReqDTO.RegisterReqDTO request
    ){
        MemberResDTO.RegisterResDTO result = memberService.register(request);
        return ApiResponse.onSuccess(MemberSuccessCode.OK, result);
    }

    @PostMapping("/join")
    public ApiResponse<MemberResDTO.JoinResultDTO> join(
            @RequestBody MemberReqDTO.JoinDTO request
    ) {
        System.out.println("회원가입 시도 이메일: " + request.email());

        Member member = memberService.joinMember(request);

        return ApiResponse.onSuccess(MemberSuccessCode.OK, MemberConverter.toJoinResultDTO(member));
    }
}
