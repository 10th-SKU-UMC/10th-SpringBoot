package com.example.week07.domain.member.dto;

import lombok.Builder;

import java.util.List;

public class MemberResDTO {

    // 회원가입 응답 데이터
    @Builder
    public record SignUpRes(
            Long memberId, // 생성된 회원 ID
            String name    // 회원 이름
    ) {}

    // 홈 화면 - 도전 가능한 미션 하나의 정보
    @Builder
    public record AvailableMissionInfo(
            Long missionId,      // 미션 ID
            String storeName,    // 가게 이름
            String conditional,  // 미션 조건 (예: 10,000원 이상의 식사시)
            Integer point,       // 적립 포인트
            Long dDay            // 마감까지 남은 일수
    ) {}

    // 홈 화면 응답 데이터
    @Builder
    public record HomeInfo(
            String address,                          // 선택된 지역명
            Integer point,                           // 보유 포인트
            Integer completedMissionCount,           // 완료한 미션 수 (7/10에서 7)
            Integer missionGoal,                     // 목표 미션 수 (고정 10)
            Integer bonusPoint,                      // 목표 달성 보너스 포인트 (고정 1000)
            List<AvailableMissionInfo> missions,     // 도전 가능한 미션 목록
            Boolean hasNext                          // 다음 페이지 여부
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
