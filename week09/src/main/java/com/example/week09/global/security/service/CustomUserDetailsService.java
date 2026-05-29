package com.example.week09.global.security.service;

import com.example.week09.domain.member.entity.Member;
import com.example.week09.domain.member.enums.SocialType;
import com.example.week09.domain.member.exception.MemberException;
import com.example.week09.domain.member.exception.code.MemberErrorCode;
import com.example.week09.domain.member.repository.MemberRepository;
import com.example.week09.global.security.entity.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Spring Security 인증 시 사용자를 조회하는 서비스
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(
            String username
    ) throws UsernameNotFoundException {
        // 이메일로 회원 조회 (폼 로그인용)
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        return new AuthMember(member);
    }

    // JWT 필터에서 소셜 UID + 소셜 타입으로 회원 조회 (OAuth 기반)
    public UserDetails loadUserByUidAndSocialType(
            SocialType socialType, String uid
    ) throws UsernameNotFoundException {
        Member member = memberRepository.findBySocialTypeAndSocialUid(socialType, uid)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        return new AuthMember(member);
    }
}
