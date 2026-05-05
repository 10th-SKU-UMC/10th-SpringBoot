package com.example.umc10th.domain.member.controller;

//import com.example.umc10th.domain.member.dto.MemberRequest;
//import com.example.umc10th.domain.member.dto.MemberResponse;
//import com.example.umc10th.domain.member.service.MemberService;
//import com.example.umc10th.global.apiPayload.ApiResponse;
//import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
//import com.example.umc10th.global.apiPayload.code.GeneralSuccessCode;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/members")
//public class MemberController {
//
//    private final MemberService memberService;
//
//    @GetMapping("/test")
//    public ApiResponse<String> test(
//
//    ) throws Exception {
//        throw new Exception("test");
//    }
//
//    @PostMapping("/query-parameter")
//    public ApiResponse<String> exception(
//            @RequestParam String queryParameter
//    ) {
//        BaseSuccessCode code = GeneralSuccessCode.OK;
//        return ApiResponse.onSuccess(code, memberService.singleParameter(queryParameter));
//    }
//
//    @PostMapping("/request-body")
//    public MemberResponse.RequestBody requestBody(
//            @RequestBody MemberRequest.RequestBody dto
//    ) {
//        return memberService.requestBody(dto);
//    }
//
//    @PostMapping("/{pathVariable}")
//    public String pathVariable(
//            @PathVariable String pathVariable
//    ) {
//        return memberService.singleParameter(pathVariable);
//    }
//
//    @PostMapping("/header")
//    public String header(
//            @RequestHeader("test") String test
//    ) {
//        return memberService.singleParameter(test);
//    }
//
//}

import com.example.umc10th.domain.member.dto.MemberRequest;
import com.example.umc10th.domain.member.dto.MemberResponse;
import com.example.umc10th.domain.member.exception.MemberSuccessCode;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class MemberController {

    private final MemberService memberService;

    // 마이페이지
    @PostMapping("/v1/users/me")
    public ApiResponse<MemberResponse.GetInfo> getInfo(
            @RequestBody MemberRequest.GetInfo dto
    ){
        BaseSuccessCode code = MemberSuccessCode.OK;
        return ApiResponse.onSuccess(code, memberService.getInfo(dto));
    }
}