package com.example.week09.domain.member.repository;

import com.example.week09.domain.member.entity.Member;
import com.example.week09.domain.member.enums.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 이메일로 회원 조회 (Spring Security 인증에 사용)
    Optional<Member> findByEmail(String email);

    // 소셜 타입 + 소셜 UID로 회원 조회 (OAuth 로그인 시 기존 회원 확인)
    Optional<Member> findBySocialTypeAndSocialUid(SocialType socialType, String socialUid);
}
