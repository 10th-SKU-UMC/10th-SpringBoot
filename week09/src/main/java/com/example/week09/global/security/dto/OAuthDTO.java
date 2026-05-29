package com.example.week09.global.security.dto;

import com.example.week09.domain.member.enums.SocialType;

// 여러 OAuth 제공자(카카오, 네이버 등)의 유저 정보를 통일하는 공통 인터페이스
public interface OAuthDTO {

    SocialType getSocialType(); // 소셜 로그인 제공자 타입 (KAKAO 등)

    String getSocialUid();     // 제공자가 부여한 고유 ID

    String getSocialEmail();   // 이메일

    String getName();          // 닉네임
}
