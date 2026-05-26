package com.example.umc10th.member.controller;

import com.example.umc10th.global.response.ApiResponse;
import com.example.umc10th.global.security.AuthMember;
import com.example.umc10th.member.dto.MemberResDTO;
import com.example.umc10th.member.service.MemberService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/api/v2/users/me")
    public ApiResponse<MemberResDTO.GetInfo> getInfo(@AuthenticationPrincipal AuthMember member) {
        MemberResDTO.GetInfo response = memberService.getInfo(member);
        return ApiResponse.onSuccess("MEMBER200_1", "성공적으로 유저를 조회했습니다.", response);
    }

	@GetMapping("/users/{userId}/home")
	public ApiResponse<MemberResDTO.HomeResponse> getHome(@PathVariable Long userId) {
		MemberResDTO.HomeResponse response = memberService.getHome(userId);
		return ApiResponse.onSuccess("HOME200", "홈 조회 성공", response);
	}
}
