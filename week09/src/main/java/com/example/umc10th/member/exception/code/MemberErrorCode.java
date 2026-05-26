package com.example.umc10th.member.exception.code;

import com.example.umc10th.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum MemberErrorCode implements ErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404", "회원을 찾을 수 없습니다."),
    MEMBER_DUPLICATE_EMAIL(HttpStatus.CONFLICT, "MEMBER409", "이미 사용 중인 이메일입니다."),
    MEMBER_INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "MEMBER401", "이메일 또는 비밀번호가 올바르지 않습니다."),
    MEMBER_BAD_REQUEST(HttpStatus.BAD_REQUEST, "MEMBER400", "잘못된 회원 요청입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    MemberErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
