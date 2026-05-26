package com.example.week08.domain.member.service;

import com.example.week08.domain.member.converter.MemberConverter;
import com.example.week08.domain.member.dto.MemberReqDTO;
import com.example.week08.domain.member.dto.MemberResDTO;
import com.example.week08.domain.member.entity.Food;
import com.example.week08.domain.member.entity.Member;
import com.example.week08.domain.member.entity.Term;
import com.example.week08.domain.member.entity.mapping.MemberFood;
import com.example.week08.domain.member.entity.mapping.MemberTerm;
import com.example.week08.domain.member.enums.FoodName;
import com.example.week08.domain.member.enums.TermName;
import com.example.week08.domain.member.exception.MemberException;
import com.example.week08.domain.member.exception.code.MemberErrorCode;
import com.example.week08.domain.member.repository.*;
import com.example.week08.domain.mission.entity.Mission;
import com.example.week08.domain.mission.repository.MemberMissionRepository;
import com.example.week08.domain.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MissionRepository missionRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final FoodRepository foodRepository;
    private final TermRepository termRepository;
    private final MemberFoodRepository memberFoodRepository;
    private final MemberTermRepository memberTermRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 - 이메일 중복 확인 → Member 저장 → 약관/음식 저장
    @Transactional
    public MemberResDTO.SignUpRes signUp(MemberReqDTO.SignUp dto) {
        // 이메일 중복 확인
        if (memberRepository.findByEmail(dto.email()).isPresent()) {
            throw new MemberException(MemberErrorCode.DUPLICATE_EMAIL);
        }

        // 비밀번호 BCrypt 암호화 후 Member 저장
        String encodedPassword = passwordEncoder.encode(dto.password());
        Member member = memberRepository.save(MemberConverter.toMember(dto, encodedPassword));

        // 동의한 약관 저장 (true인 항목만 MemberTerm 생성)
        saveTermIfAgreed(member, TermName.AGE, dto.agreeAge());
        saveTermIfAgreed(member, TermName.SERVICE, dto.agreeService());
        saveTermIfAgreed(member, TermName.PRIVACY, dto.agreePrivacy());
        saveTermIfAgreed(member, TermName.LOCATION, dto.agreeLocation());
        saveTermIfAgreed(member, TermName.MARKETING, dto.agreeMarketing());

        // 선호 음식 저장
        if (dto.favoriteFoods() != null) {
            dto.favoriteFoods().forEach(foodNameStr -> {
                FoodName foodName = FoodName.valueOf(foodNameStr);
                // Food 없으면 새로 생성
                Food food = foodRepository.findByName(foodName)
                        .orElseGet(() -> foodRepository.save(Food.builder().name(foodName).build()));
                memberFoodRepository.save(MemberFood.builder().member(member).food(food).build());
            });
        }

        return MemberConverter.toSignUpRes(member);
    }

    // 약관 동의 여부 확인 후 MemberTerm 저장
    private void saveTermIfAgreed(Member member, TermName termName, Boolean agreed) {
        if (Boolean.TRUE.equals(agreed)) {
            // Term 없으면 새로 생성
            Term term = termRepository.findByName(termName)
                    .orElseGet(() -> termRepository.save(Term.builder().name(termName).build()));
            memberTermRepository.save(MemberTerm.builder().member(member).term(term).build());
        }
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
