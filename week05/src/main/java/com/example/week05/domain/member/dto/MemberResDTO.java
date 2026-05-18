package com.example.week05.domain.member.dto;

import lombok.Builder;

public class MemberResDTO {

    // 회원가입 응답 데이터
    @Builder
    public record SignUpRes(
            Long memberId, // 생성된 회원 ID
            String name    // 회원 이름
    ) {}

    // 홈 화면 응답 데이터
    @Builder
    public record HomeInfo(
            String name,                 // 회원 이름
            Integer point,               // 보유 포인트
            Integer ongoingMissionCount  // 진행 중인 미션 수
    ) {}

    // 마이페이지 응답 데이터
    @Builder
    public record MyPageInfo(
            String name,        // 이름
            String profileUrl,  // 프로필 이미지 URL
            String email,       // 이메일
            String phoneNumber, // 전화번호
            Integer point       // 보유 포인트
    ) {}

    // 닉네임 변경 응답 데이터
    @Builder
    public record UpdateNicknameRes(
            Long memberId, // 회원 ID
            String nickname // 변경된 닉네임
    ) {}

    // 전화번호 변경 응답 데이터
    @Builder
    public record UpdatePhoneRes(
            Long memberId,     // 회원 ID
            String phoneNumber // 변경된 전화번호
    ) {}

    // 알림 설정 변경 응답 데이터
    @Builder
    public record UpdateNotificationRes(
            Long memberId,              // 회원 ID
            Boolean notificationEnabled // 변경된 알림 설정 (true/false)
    ) {}
}
