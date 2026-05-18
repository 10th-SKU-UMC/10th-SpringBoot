package com.example.week07.domain.member.controller;

import com.example.week07.domain.member.dto.MemberReqDTO;
import com.example.week07.domain.member.dto.MemberResDTO;
import com.example.week07.domain.member.exception.code.MemberSuccessCode;
import com.example.week07.domain.member.service.MemberService;
import com.example.week07.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController // JSON 형식으로 응답하는 컨트롤러
@RequiredArgsConstructor // memberService를 생성자로 자동 주입
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    // POST /auth/users/signup
    @PostMapping("/auth/users/signup")
    public ApiResponse<MemberResDTO.SignUpRes> signUp(
            @RequestBody MemberReqDTO.SignUp dto // 요청 바디에서 회원가입 데이터 받기
    ) {
        return ApiResponse.onSuccess(MemberSuccessCode.SIGN_UP_OK, memberService.signUp(dto));
    }

    // 홈 화면 조회 (선택 지역 미도전 미션 목록, 페이징)
    // GET /home?memberId=1&page=0&size=10
    @GetMapping("/home")
    public ApiResponse<MemberResDTO.HomeInfo> getHome(
            @RequestParam Long memberId,                        // 쿼리 파라미터로 회원 ID 받기 (임시, 추후 JWT 대체)
            @RequestParam(defaultValue = "0") int page,        // 페이지 번호 (0부터 시작)
            @RequestParam(defaultValue = "10") int size        // 페이지 크기 (기본값 10)
    ) {
        return ApiResponse.onSuccess(MemberSuccessCode.GET_HOME_OK, memberService.getHome(memberId, page, size));
    }

    // 마이페이지 조회
    // GET /mypage?memberId=1
    @GetMapping("/mypage")
    public ApiResponse<MemberResDTO.MyPageInfo> getMyPage(
            @RequestParam Long memberId // 쿼리 파라미터로 회원 ID 받기 (임시, 추후 JWT 대체)
    ) {
        return ApiResponse.onSuccess(MemberSuccessCode.GET_MY_PAGE_OK, memberService.getMyPage(memberId));
    }

    // 닉네임 변경
    // PATCH /mypage/nickname
    @PatchMapping("/mypage/nickname")
    public ApiResponse<MemberResDTO.UpdateNicknameRes> updateNickname(
            @RequestBody MemberReqDTO.UpdateNickname dto // 요청 바디에서 변경할 닉네임 받기
    ) {
        return ApiResponse.onSuccess(MemberSuccessCode.UPDATE_NICKNAME_OK, memberService.updateNickname(dto));
    }

    // 휴대폰 번호 변경
    // PATCH /mypage/phone
    @PatchMapping("/mypage/phone")
    public ApiResponse<MemberResDTO.UpdatePhoneRes> updatePhone(
            @RequestBody MemberReqDTO.UpdatePhone dto // 요청 바디에서 변경할 전화번호 받기
    ) {
        return ApiResponse.onSuccess(MemberSuccessCode.UPDATE_PHONE_OK, memberService.updatePhone(dto));
    }

    // 알림 설정 변경
    // PATCH /mypage/notifications
    @PatchMapping("/mypage/notifications")
    public ApiResponse<MemberResDTO.UpdateNotificationRes> updateNotification(
            @RequestBody MemberReqDTO.UpdateNotification dto // 요청 바디에서 알림 설정값 받기
    ) {
        return ApiResponse.onSuccess(MemberSuccessCode.UPDATE_NOTIFICATION_OK, memberService.updateNotification(dto));
    }

    // 1:1 문의 생성
    // POST /inquiries
    @PostMapping("/inquiries")
    public ApiResponse<Void> createInquiry(
            @RequestBody MemberReqDTO.CreateInquiry dto // 요청 바디에서 문의 내용 받기
    ) {
        memberService.createInquiry(dto);
        return ApiResponse.onSuccess(MemberSuccessCode.CREATE_INQUIRY_OK, null);
    }

    // 로그아웃
    // POST /auth/logout?memberId=1
    @PostMapping("/auth/logout")
    public ApiResponse<Void> logout(
            @RequestParam Long memberId // 쿼리 파라미터로 회원 ID 받기 (임시, 추후 JWT 대체)
    ) {
        memberService.logout(memberId);
        return ApiResponse.onSuccess(MemberSuccessCode.LOGOUT_OK, null);
    }

    // 회원 탈퇴
    // DELETE /users/me?memberId=1
    @DeleteMapping("/users/me")
    public ApiResponse<Void> deleteMember(
            @RequestParam Long memberId // 쿼리 파라미터로 회원 ID 받기 (임시, 추후 JWT 대체)
    ) {
        memberService.deleteMember(memberId);
        return ApiResponse.onSuccess(MemberSuccessCode.DELETE_OK, null);
    }
}
