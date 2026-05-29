package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member API", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /* ========== 인증 (Auth) — JWT 미구현으로 stub 유지 ========== */

    @Operation(summary = "회원가입")
    @PostMapping("/api/auth/signup")
    public ApiResponse<MemberResDTO.SignupResultDTO> signup(
            @RequestBody @Valid MemberReqDTO.SignupDTO request) {
        return ApiResponse.onSuccess(SuccessCode.CREATED, memberService.signup(request));
    }

    @Operation(summary = "일반 로그인")
    @PostMapping("/api/auth/login")
    public ApiResponse<MemberResDTO.LoginResultDTO> login(
            @RequestBody @Valid MemberReqDTO.LoginDTO request) {
        return ApiResponse.onSuccess((MemberResDTO.LoginResultDTO) null);
    }

    @Operation(summary = "소셜 로그인", description = "provider: KAKAO, APPLE, GOOGLE")
    @PostMapping("/api/auth/social/{provider}")
    public ApiResponse<MemberResDTO.SocialLoginResultDTO> socialLogin(
            @Parameter(description = "소셜 로그인 제공자") @PathVariable String provider,
            @RequestBody @Valid MemberReqDTO.SocialLoginDTO request) {
        return ApiResponse.onSuccess((MemberResDTO.SocialLoginResultDTO) null);

    }

    @Operation(summary = "로그아웃")
    @PostMapping("/api/auth/logout")
    public ApiResponse<Void> logout(
            @RequestBody MemberReqDTO.TokenRefreshDTO request) {
        return ApiResponse.onSuccess(SuccessCode.OK);
    }

    @Operation(summary = "토큰 재발급")
    @PostMapping("/api/auth/token/refresh")
    public ApiResponse<MemberResDTO.TokenRefreshResultDTO> refreshToken(
            @RequestBody @Valid MemberReqDTO.TokenRefreshDTO request) {
        return ApiResponse.onSuccess((MemberResDTO.TokenRefreshResultDTO) null);

    }

    @Operation(summary = "회원탈퇴")
    @DeleteMapping("/api/auth/withdraw")
    public ApiResponse<Void> withdraw() {
        return ApiResponse.onSuccess(SuccessCode.OK);
    }

    /* ========== 온보딩 ========== */

    @Operation(summary = "음식 선호 설정")
    @PostMapping("/api/users/preferences/food")
    public ApiResponse<Void> setFoodPreference(
            @RequestBody @Valid MemberReqDTO.FoodPreferenceDTO request) {
        return ApiResponse.onSuccess(SuccessCode.OK);
    }

    /* ========== 유저 정보 (마이페이지) ========== */

    @Operation(summary = "내 프로필 조회", description = "마이페이지 — 닉네임·이메일·포인트 등 조회")
    @GetMapping("/api/users/me")
    public ApiResponse<MemberResDTO.MemberInfoDTO> getMyProfile(
            @Parameter(description = "회원 ID", required = true) @RequestHeader("X-Member-Id") Long memberId) {
        return ApiResponse.onSuccess(SuccessCode.MEMBER_FOUND, memberService.getMyProfile(memberId));
    }

    @Operation(summary = "내 정보 수정")
    @PatchMapping("/api/users/me")
    public ApiResponse<MemberResDTO.MemberInfoDTO> updateMyProfile(
            @Parameter(description = "회원 ID", required = true) @RequestHeader("X-Member-Id") Long memberId,
            @RequestBody @Valid MemberReqDTO.UpdateDTO request) {
        return ApiResponse.onSuccess(SuccessCode.MEMBER_UPDATED, memberService.updateMyProfile(memberId, request));
    }

    @Operation(summary = "포인트 내역 조회")
    @GetMapping("/api/users/me/points")
    public ApiResponse<MemberResDTO.PointHistoryListDTO> getPointHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.onSuccess((MemberResDTO.PointHistoryListDTO) null);
    }

    @Operation(summary = "알림 설정 조회", description = "마이페이지 — 알림 설정 조회")
    @GetMapping("/api/users/notifications/settings")
    public ApiResponse<MemberResDTO.NotificationSettingDTO> getNotificationSettings(
            @Parameter(description = "회원 ID", required = true) @RequestHeader("X-Member-Id") Long memberId) {
        return ApiResponse.onSuccess(memberService.getNotificationSettings(memberId));
    }

    @Operation(summary = "알림 설정 변경")
    @PatchMapping("/api/users/notifications/settings")
    public ApiResponse<MemberResDTO.NotificationSettingDTO> updateNotificationSettings(
            @Parameter(description = "회원 ID", required = true) @RequestHeader("X-Member-Id") Long memberId,
            @RequestBody MemberReqDTO.NotificationSettingDTO request) {
        return ApiResponse.onSuccess(memberService.updateNotificationSettings(memberId, request));
    }

    @Operation(summary = "약관 동의")
    @PostMapping("/api/users/agreements")
    public ApiResponse<Void> agreeToTerm(
            @RequestBody @Valid MemberReqDTO.AgreementDTO request) {
        return ApiResponse.onSuccess(SuccessCode.OK);
    }

    @Operation(summary = "약관 동의 목록 조회")
    @GetMapping("/api/users/agreements")
    public ApiResponse<MemberResDTO.AgreementListDTO> getAgreements(
            @Parameter(description = "회원 ID", required = true) @RequestHeader("X-Member-Id") Long memberId) {
        return ApiResponse.onSuccess(memberService.getAgreements(memberId));
    }
}
