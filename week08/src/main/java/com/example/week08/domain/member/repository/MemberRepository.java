package com.example.week08.domain.member.repository;

import com.example.week08.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 이메일로 회원 조회 (Spring Security 인증에 사용)
    Optional<Member> findByEmail(String email);
}
