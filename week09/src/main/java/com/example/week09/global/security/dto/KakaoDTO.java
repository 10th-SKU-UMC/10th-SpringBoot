package com.example.week09.global.security.dto;

import com.example.week09.domain.member.enums.SocialType;
import lombok.RequiredArgsConstructor;

// 카카오 OAuth 유저 정보를 OAuthDTO로 매핑하는 구현체
@RequiredArgsConstructor
public class KakaoDTO implements OAuthDTO {

    private final String id;    // 카카오 고유 ID
    private final String email; // 카카오 이메일
    private final String name;  // 카카오 닉네임

    @Override
    public SocialType getSocialType() {
        return SocialType.KAKAO;
    }

    @Override
    public String getSocialUid() {
        return id;
    }

    @Override
    public String getSocialEmail() {
        return email;
    }

    @Override
    public String getName() {
        return name;
    }
}
