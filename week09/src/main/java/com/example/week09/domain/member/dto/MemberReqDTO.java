package com.example.week09.domain.member.dto;

import java.util.List;

public class MemberReqDTO {

    // 회원가입 요청 데이터
    public record SignUp(
            Boolean agreeAge,          // 만 14세 이상 동의
            Boolean agreeService,      // 서비스 이용약관 동의
            Boolean agreePrivacy,      // 개인정보 처리방침 동의
            Boolean agreeLocation,     // 위치정보 이용 동의
            Boolean agreeMarketing,    // 마케팅 수신 동의
            String email,              // 이메일 (폼 로그인 ID)
            String password,           // 비밀번호 (BCrypt 암호화 전 원문)
            String name,               // 이름
            String gender,             // 성별 (MALE / FEMALE / NONE)
            String birth,              // 생년월일 (예: 2003-10-01)
            String address,            // 주소 (Address enum 이름)
            String detailAddress,      // 상세 주소
            List<String> favoriteFoods // 선호 음식 카테고리 목록 (FoodName enum 이름)
    ) {}

    // 닉네임 변경 요청 데이터
    public record UpdateNickname(
            Long memberId,   // 임시 - 나중에 JWT 토큰으로 대체 예정
            String nickname  // 변경할 닉네임
    ) {}

    // 휴대폰 번호 변경 요청 데이터
    public record UpdatePhone(
            Long memberId,          // 임시 - 나중에 JWT 토큰으로 대체 예정
            String phone,           // 변경할 전화번호
            String verificationCode // 인증번호
    ) {}

    // 알림 설정 변경 요청 데이터
    public record UpdateNotification(
            Long memberId,            // 임시 - 나중에 JWT 토큰으로 대체 예정
            Boolean eventAlert,       // 이벤트 알림
            Boolean reviewReplyAlert, // 리뷰 답글 알림
            Boolean inquiryReplyAlert // 문의 답글 알림
    ) {}

    // 로그인 요청 데이터
    public record Login(
            String email,    // 이메일
            String password  // 비밀번호 (원문)
    ) {}

    // 1:1 문의 생성 요청 데이터
    public record CreateInquiry(
            Long memberId, // 임시 - 나중에 JWT 토큰으로 대체 예정
            String title,  // 문의 제목
            String content // 문의 내용
    ) {}
}
