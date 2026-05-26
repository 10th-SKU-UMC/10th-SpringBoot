package com.umc10th.umc10th.global.sequrity.service;

import com.umc10th.umc10th.domain.user.converter.UserConverter;
import com.umc10th.umc10th.domain.user.entity.User;
import com.umc10th.umc10th.domain.user.enums.Provider;
import com.umc10th.umc10th.domain.user.exception.UserException;
import com.umc10th.umc10th.domain.user.exception.code.UserErrorCode;
import com.umc10th.umc10th.domain.user.repository.UserRepository;
import com.umc10th.umc10th.global.sequrity.dto.KakaoDTO;
import com.umc10th.umc10th.global.sequrity.dto.OAuthDTO;
import com.umc10th.umc10th.global.sequrity.entity.OAuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuthService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(
            OAuth2UserRequest userRequest
    ) throws OAuth2AuthenticationException {
        // (필수) 인증 서버의 일회성 토큰을 이용해 정보 조회 & 유저 객체 생성
        OAuth2User oAuthMember = super.loadUser(userRequest);

        // 제공자 ID 추출
        Provider providerId;
        String socialUid;
        try {
            providerId = Provider.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
            socialUid = String.valueOf((Long) oAuthMember.getAttribute("id"));
        } catch (IllegalArgumentException e) {
            throw new UserException(UserErrorCode.NOT_SUPPORT_SOCIAL_PROVIDER);
        }

        // 제공자별 유저 정보 추출
        OAuthDTO dto;
        switch (providerId) {
            case KAKAO -> {
                Map<String, Object> kakaoAccount = oAuthMember.getAttribute("kakao_account");
                if (kakaoAccount == null) {
                    throw new UserException(UserErrorCode.NOT_SUPPORT_SOCIAL_PROVIDER);
                }
                Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
                // 이메일 미동의 시 socialUid 기반 대체값 사용
                Object emailObj = kakaoAccount.get("email");
                String email = (emailObj != null) ? emailObj.toString() : socialUid + "@kakao.com";
                String name = profile.get("nickname").toString();
                dto = new KakaoDTO(socialUid, email, name);
            }
            default -> throw new UserException(UserErrorCode.NOT_SUPPORT_SOCIAL_PROVIDER);
        }

        // DB 저장: 있다면 그 데이터 가져오고 없으면 새로 저장
        User user = userRepository.findBySocialProviderAndSocialUid(providerId, socialUid)
                .orElseGet(() -> {
                    User newUser = UserConverter.toUser(dto);
                    return userRepository.save(newUser);
                });

        return new OAuthUser(user, oAuthMember.getAttributes());
    }
}
