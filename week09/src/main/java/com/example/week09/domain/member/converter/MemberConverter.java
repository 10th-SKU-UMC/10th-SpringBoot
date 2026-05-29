package com.example.week09.domain.member.converter;

import com.example.week09.domain.member.dto.MemberReqDTO;
import com.example.week09.domain.member.dto.MemberResDTO;
import com.example.week09.domain.member.entity.Member;
import com.example.week09.domain.member.enums.Gender;
import com.example.week09.domain.member.enums.SocialType;
import com.example.week09.domain.mission.entity.Mission;
import com.example.week09.global.enums.Address;
import com.example.week09.global.security.dto.OAuthDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class MemberConverter {

    // OAuthDTO → Member 엔티티 변환 (OAuth 신규 회원 가입)
    public static Member toMember(OAuthDTO dto) {
        return Member.builder()
                .email(dto.getSocialEmail())
                .name(dto.getName())
                .socialUid(dto.getSocialUid())
                .socialType(dto.getSocialType())
                .profileUrl("")       // 기본값 (추후 프로필 업로드 구현)
                .birth(LocalDate.of(2000, 1, 1)) // 기본값 (OAuth에서 제공 안 함)
                .detailAddress("")    // 기본값 (OAuth에서 제공 안 함)
                .build();
    }

    // accessToken → 로그인 응답 DTO 변환 (OAuth 로그인)
    public static MemberResDTO.Login toLogin(String accessToken) {
        return MemberResDTO.Login.builder()
                .accessToken(accessToken)
                .build();
    }

    // SignUp DTO + 암호화된 비밀번호 → Member 엔티티 변환
    public static Member toMember(MemberReqDTO.SignUp dto, String encodedPassword) {
        return Member.builder()
                .email(dto.email())
                .password(encodedPassword)
                .name(dto.name())
                .gender(Gender.valueOf(dto.gender()))           // "MALE" / "FEMALE" / "NONE"
                .birth(LocalDate.parse(dto.birth()))           // "2003-10-01" 형식
                .address(Address.valueOf(dto.address()))       // Address enum 이름
                .detailAddress(dto.detailAddress())
                .socialUid("")                                 // 폼 로그인은 소셜 UID 없음
                .socialType(SocialType.NONE)                  // 폼 로그인은 소셜 타입 NONE
                .profileUrl("")                               // 기본값 (추후 프로필 업로드 구현)
                .build();
    }

    // Member 엔티티 → 회원가입 응답 DTO 변환
    public static MemberResDTO.SignUpRes toSignUpRes(Member member) {
        return MemberResDTO.SignUpRes.builder()
                .memberId(member.getId())
                .name(member.getName())
                .build();
    }

    // Member 엔티티 → JWT 기반 마이페이지 응답 DTO 변환 (v2)
    public static MemberResDTO.GetInfo toGetInfo(Member member) {
        return MemberResDTO.GetInfo.builder()
                .name(member.getName())
                .profileUrl(member.getProfileUrl())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .point(member.getPoint())
                .build();
    }

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
