package com.example.umc10th.domain.member.controller;


import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
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

    // TODO: MemberService 주입 (다음 주차)
    // private final MemberService memberService;

    /* ========== 인증 (Auth) ========== */

    @Operation(summary = "회원가입")
    @PostMapping("/api/auth/signup")
    public ApiResponse<MemberResDTO.SignupResultDTO> signup(
            @RequestBody @Valid MemberReqDTO.SignupDTO request) {
        // TODO: return ApiResponse.onSuccess(SuccessCode.CREATED, memberService.signup(request));
        return ApiResponse.onSuccess(SuccessCode.CREATED, null);
    }

    @Operation(summary = "일반 로그인")
    @PostMapping("/api/auth/login")
    public ApiResponse<MemberResDTO.LoginResultDTO> login(
            @RequestBody @Valid MemberReqDTO.LoginDTO request) {
        // TODO: return ApiResponse.onSuccess(memberService.login(request));
        return ApiResponse.onSuccess(null);
    }

    @Operation(summary = "소셜 로그인", description = "provider: KAKAO, APPLE, GOOGLE")
    @PostMapping("/api/auth/social/{provider}")
    public ApiResponse<MemberResDTO.SocialLoginResultDTO> socialLogin(
            @Parameter(description = "소셜 로그인 제공자") @PathVariable String provider,
            @RequestBody @Valid MemberReqDTO.SocialLoginDTO request) {
        // TODO: return ApiResponse.onSuccess(memberService.socialLogin(provider, request));
        return ApiResponse.onSuccess(null);
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/api/auth/logout")
    public ApiResponse<Void> logout(
            @RequestBody MemberReqDTO.TokenRefreshDTO request) {
        // TODO: memberService.logout(request.getRefreshToken());
        return ApiResponse.onSuccess(SuccessCode.OK);
    }

    @Operation(summary = "토큰 재발급")
    @PostMapping("/api/auth/token/refresh")
    public ApiResponse<MemberResDTO.TokenRefreshResultDTO> refreshToken(
            @RequestBody @Valid MemberReqDTO.TokenRefreshDTO request) {
        // TODO: return ApiResponse.onSuccess(memberService.refreshToken(request));
        return ApiResponse.onSuccess(null);
    }

    @Operation(summary = "회원탈퇴")
    @DeleteMapping("/api/auth/withdraw")
    public ApiResponse<Void> withdraw() {
        // TODO: memberService.withdraw(currentMemberId);
        return ApiResponse.onSuccess(SuccessCode.OK);
    }

    /* ========== 온보딩 ========== */

    @Operation(summary = "음식 선호 설정")
    @PostMapping("/api/users/preferences/food")
    public ApiResponse<Void> setFoodPreference(
            @RequestBody @Valid MemberReqDTO.FoodPreferenceDTO request) {
        // TODO: memberService.setFoodPreference(currentMemberId, request);
        return ApiResponse.onSuccess(SuccessCode.OK);
    }

    /* ========== 유저 정보 ========== */

    @Operation(summary = "내 프로필 조회")
    @GetMapping("/api/users/me")
    public ApiResponse<MemberResDTO.MemberInfoDTO> getMyProfile() {
        // TODO: return ApiResponse.onSuccess(SuccessCode.MEMBER_FOUND, memberService.getMyProfile(currentMemberId));
        return ApiResponse.onSuccess(SuccessCode.MEMBER_FOUND, null);
    }

    @Operation(summary = "내 정보 수정")
    @PatchMapping("/api/users/me")
    public ApiResponse<MemberResDTO.MemberInfoDTO> updateMyProfile(
            @RequestBody @Valid MemberReqDTO.UpdateDTO request) {
        // TODO: return ApiResponse.onSuccess(SuccessCode.MEMBER_UPDATED, memberService.updateMyProfile(currentMemberId, request));
        return ApiResponse.onSuccess(SuccessCode.MEMBER_UPDATED, null);
    }

    @Operation(summary = "포인트 내역 조회")
    @GetMapping("/api/users/me/points")
    public ApiResponse<MemberResDTO.PointHistoryListDTO> getPointHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // TODO: return ApiResponse.onSuccess(memberService.getPointHistory(currentMemberId, page, size));
        return ApiResponse.onSuccess(null);
    }

    @Operation(summary = "알림 설정 조회")
    @GetMapping("/api/users/notifications/settings")
    public ApiResponse<MemberResDTO.NotificationSettingDTO> getNotificationSettings() {
        // TODO: return ApiResponse.onSuccess(memberService.getNotificationSettings(currentMemberId));
        return ApiResponse.onSuccess(null);
    }

    @Operation(summary = "알림 설정 변경")
    @PatchMapping("/api/users/notifications/settings")
    public ApiResponse<MemberResDTO.NotificationSettingDTO> updateNotificationSettings(
            @RequestBody MemberReqDTO.NotificationSettingDTO request) {
        // TODO: return ApiResponse.onSuccess(memberService.updateNotificationSettings(currentMemberId, request));
        return ApiResponse.onSuccess(null);
    }

    @Operation(summary = "약관 동의")
    @PostMapping("/api/users/agreements")
    public ApiResponse<Void> agreeToTerm(
            @RequestBody @Valid MemberReqDTO.AgreementDTO request) {
        // TODO: memberService.agreeToTerm(currentMemberId, request);
        return ApiResponse.onSuccess(SuccessCode.OK);
    }

    @Operation(summary = "약관 동의 목록 조회")
    @GetMapping("/api/users/agreements")
    public ApiResponse<MemberResDTO.AgreementListDTO> getAgreements() {
        // TODO: return ApiResponse.onSuccess(memberService.getAgreements(currentMemberId));
        return ApiResponse.onSuccess(null);
    }
}
