package com.example.week06.domain.member.service;

import com.example.week06.domain.member.converter.MemberConverter;
import com.example.week06.domain.member.dto.MemberReqDTO;
import com.example.week06.domain.member.dto.MemberResDTO;
import com.example.week06.domain.member.entity.Member;
import com.example.week06.domain.member.exception.MemberException;
import com.example.week06.domain.member.exception.code.MemberErrorCode;
import com.example.week06.domain.member.repository.MemberRepository;
import com.example.week06.domain.mission.entity.Mission;
import com.example.week06.domain.mission.repository.MemberMissionRepository;
import com.example.week06.domain.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MissionRepository missionRepository;
    private final MemberMissionRepository memberMissionRepository;

    // 회원가입 - 다음 주차에 구현 예정
    public MemberResDTO.SignUpRes signUp(MemberReqDTO.SignUp dto) {
        return null;
    }

    // 홈 화면 조회 - 선택 지역 기반 미도전 미션 목록 (페이징)
    public MemberResDTO.HomeInfo getHome(Long memberId, int page, int size) {
        // 회원 조회 - 없으면 예외 발생
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // 완료한 미션 수 조회 (원형 진행률 표시용)
        int completedCount = memberMissionRepository.countCompletedMissions(memberId);

        // 회원의 선택 지역에서 아직 도전하지 않은 미션 목록 페이징 조회
        Page<Mission> availableMissions = missionRepository.findAvailableMissionsByAddress(
                member.getAddress(),
                memberId,
                PageRequest.of(page, size)
        );

        return MemberConverter.toHomeInfo(member, completedCount, availableMissions);
    }

    // 마이페이지 조회
    public MemberResDTO.MyPageInfo getMyPage(Long memberId) {
        // 회원 조회 - 없으면 예외 발생
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        return MemberConverter.toMyPageInfo(member);
    }

    // 닉네임 변경 - 다음 주차에 구현 예정
    public MemberResDTO.UpdateNicknameRes updateNickname(MemberReqDTO.UpdateNickname dto) {
        return null;
    }

    // 전화번호 변경 - 다음 주차에 구현 예정
    public MemberResDTO.UpdatePhoneRes updatePhone(MemberReqDTO.UpdatePhone dto) {
        return null;
    }

    // 알림 설정 변경 - 다음 주차에 구현 예정
    public MemberResDTO.UpdateNotificationRes updateNotification(MemberReqDTO.UpdateNotification dto) {
        return null;
    }

    // 1:1 문의 생성 - 다음 주차에 구현 예정
    public void createInquiry(MemberReqDTO.CreateInquiry dto) {
    }

    // 로그아웃 - 다음 주차에 구현 예정
    public void logout(Long memberId) {
    }

    // 회원 탈퇴 - 다음 주차에 구현 예정
    public void deleteMember(Long memberId) {
    }
}
