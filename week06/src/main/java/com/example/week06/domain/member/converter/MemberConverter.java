package com.example.week06.domain.member.converter;

import com.example.week06.domain.member.dto.MemberResDTO;
import com.example.week06.domain.member.entity.Member;
import com.example.week06.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class MemberConverter {

    // Member 엔티티를 마이페이지 응답 DTO로 변환
    public static MemberResDTO.MyPageInfo toMyPageInfo(Member member) {
        return MemberResDTO.MyPageInfo.builder()
                .name(member.getName())               // 닉네임
                .profileUrl(member.getProfileUrl())   // 프로필 이미지 URL
                .email(member.getEmail())             // 이메일
                .phoneNumber(member.getPhoneNumber()) // 휴대폰번호
                .point(member.getPoint())             // 포인트
                .build();
    }

    // Mission 엔티티 하나를 홈 화면용 미션 DTO로 변환
    public static MemberResDTO.AvailableMissionInfo toAvailableMissionInfo(Mission mission) {
        long dDay = ChronoUnit.DAYS.between(LocalDate.now(), mission.getDeadline()); // 마감까지 남은 일수
        return MemberResDTO.AvailableMissionInfo.builder()
                .missionId(mission.getId())
                .storeName(mission.getStore().getName())    // 가게 이름
                .conditional(mission.getConditional())      // 미션 조건
                .point(mission.getPoint())                  // 적립 포인트
                .dDay(dDay)                                 // D-day
                .build();
    }

    // 홈 화면 응답 DTO 전체 조합
    public static MemberResDTO.HomeInfo toHomeInfo(
            Member member,
            int completedMissionCount,
            Page<Mission> availableMissions
    ) {
        List<MemberResDTO.AvailableMissionInfo> missionList = availableMissions.getContent()
                .stream()
                .map(MemberConverter::toAvailableMissionInfo)
                .toList();

        return MemberResDTO.HomeInfo.builder()
                .address(member.getAddress().name())     // 선택된 지역 (enum 이름 문자열)
                .point(member.getPoint())                // 보유 포인트
                .completedMissionCount(completedMissionCount) // 완료한 미션 수
                .missionGoal(10)                         // 목표 미션 수 (고정값)
                .bonusPoint(1000)                        // 보너스 포인트 (고정값)
                .missions(missionList)                   // 도전 가능한 미션 목록
                .hasNext(availableMissions.hasNext())    // 다음 페이지 여부
                .build();
    }
}
