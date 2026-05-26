package com.umc10th.umc10th.domain.user.exception.code;

import com.umc10th.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements BaseErrorCode {


    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER404_1", "해당 사용자를 찾을 수 없습니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "USER409_1", "이미 사용 중인 이메일입니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;

}
