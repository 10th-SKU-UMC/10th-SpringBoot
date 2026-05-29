package com.example.week09.global.security.service;

import com.example.week09.domain.member.converter.MemberConverter;
import com.example.week09.domain.member.entity.Member;
import com.example.week09.domain.member.enums.SocialType;
import com.example.week09.domain.member.exception.MemberException;
import com.example.week09.domain.member.exception.code.MemberErrorCode;
import com.example.week09.domain.member.repository.MemberRepository;
import com.example.week09.global.security.dto.KakaoDTO;
import com.example.week09.global.security.dto.OAuthDTO;
import com.example.week09.global.security.entity.OAuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

// OAuth2 로그인 과정에서 인가 서버로부터 유저 정보를 가져와 DB에 저장 또는 조회하는 서비스
@Service
@RequiredArgsConstructor
public class CustomOAuthService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(
            OAuth2UserRequest userRequest
    ) throws OAuth2AuthenticationException {
        // 인가 서버의 일회성 토큰으로 유저 정보 조회
        OAuth2User oAuthMember = super.loadUser(userRequest);

        // 소셜 타입 및 UID 추출
        SocialType providerId;
        String socialUid;
        Map<String, Object> attributes = oAuthMember.getAttribute("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) attributes.get("profile");
        try {
            providerId = SocialType.valueOf(
                    userRequest.getClientRegistration().getRegistrationId().toUpperCase()
            );
            socialUid = String.valueOf(oAuthMember.getAttribute("id"));
        } catch (IllegalArgumentException e) {
            throw new MemberException(MemberErrorCode.NOT_SUPPORT_SOCIAL_PROVIDER);
        }

        // 소셜 타입에 따라 공통 DTO로 매핑
        OAuthDTO dto;
        switch (providerId) {
            case KAKAO -> {
                String email = attributes.get("email").toString();
                String name = profile.get("nickname").toString();
                dto = new KakaoDTO(socialUid, email, name);
            }
            default -> throw new MemberException(MemberErrorCode.NOT_SUPPORT_SOCIAL_PROVIDER);
        }

        // DB 조회: 기존 회원이면 반환, 신규 회원이면 저장 후 반환
        Member member = memberRepository.findBySocialTypeAndSocialUid(providerId, socialUid)
                .orElseGet(() -> memberRepository.save(MemberConverter.toMember(dto)));

        return new OAuthMember(member, oAuthMember.getAttributes());
    }
}
