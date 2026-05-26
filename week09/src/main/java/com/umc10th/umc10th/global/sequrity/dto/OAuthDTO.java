package com.umc10th.umc10th.global.sequrity.dto;

import com.umc10th.umc10th.domain.user.enums.Provider;

public interface OAuthDTO {
    Provider getSocialType();
    String getSocialUid();
    String getSocialEmail();
    String getName();
}
