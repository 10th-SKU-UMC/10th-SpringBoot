package com.example.umc10th.domain.member.exceptions;

import com.example.umc10th.global.apiPayload.code.ErrorCode;
import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {

    private final ErrorCode errorCode;

    public MemberException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public MemberException(String message) {
        super(message);
        this.errorCode = ErrorCode.BAD_REQUEST;
    }
}
