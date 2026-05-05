package com.umc10th.umc10th.domain.user.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum BaseSuccessCode implements com.umc10th.umc10th.global.apiPayload.code.BaseSuccessCode {

    OK(HttpStatus.OK, "USER200_1", "성공적으로 유저를 조회 했습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;
}
