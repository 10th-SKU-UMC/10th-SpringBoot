package com.example.week09.global.security.entity;

import com.example.week09.domain.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// Spring Security가 인증에 사용하는 사용자 정보 래퍼 클래스
@Getter
@RequiredArgsConstructor
public class AuthMember implements UserDetails {

    private final Member member;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 별도 권한 없이 빈 리스트 반환
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        // OAuth 로그인 기반이므로 비밀번호 불필요
        return null;
    }

    @Override
    public String getUsername() {
        // 소셜 UID를 사용자 식별자로 사용 (OAuth 기반)
        return member.getSocialUid();
    }
}
