package com.example.week09.global.security.entity;

import com.example.week09.domain.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

// OAuth2 로그인 성공 시 생성되는 인증 객체 (SecurityContextHolder에 저장됨)
@Getter
@RequiredArgsConstructor
public class OAuthMember implements OAuth2User {

    private final Member member;                    // DB에 저장된 회원 엔티티
    private final Map<String, Object> attributes;   // OAuth 제공자가 반환한 원본 속성 맵

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 별도 권한 없이 빈 리스트 반환
        return List.of();
    }

    @Override
    public String getName() {
        // OAuth2User의 name 식별자로 socialUid 사용
        return member.getSocialUid();
    }
}
