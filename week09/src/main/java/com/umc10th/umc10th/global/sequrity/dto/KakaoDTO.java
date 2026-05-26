package com.umc10th.umc10th.global.sequrity.dto;

import com.umc10th.umc10th.domain.user.enums.Provider;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KakaoDTO implements OAuthDTO {

    private final String socialUid;
    private final String email;
    private final String name;

    @Override
    public Provider getSocialType() {
        return Provider.KAKAO;
    }

    @Override
    public String getSocialUid() {
        return socialUid;
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
