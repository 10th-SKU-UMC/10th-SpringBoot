package com.example.week08.global.security.service;

import com.example.week08.domain.member.entity.Member;
import com.example.week08.domain.member.exception.MemberException;
import com.example.week08.domain.member.exception.code.MemberErrorCode;
import com.example.week08.domain.member.repository.MemberRepository;
import com.example.week08.global.security.entity.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Spring Security 인증 시 이메일로 사용자를 조회하는 서비스
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(
            String username
    ) throws UsernameNotFoundException {
        // 이메일로 회원 조회, 없으면 예외 발생
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        return new AuthMember(member);
    }
}
