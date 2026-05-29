package com.example.umc10th.domain.review.exception;

import com.example.umc10th.global.apiPayload.code.ErrorCode;
import lombok.Getter;

@Getter
public class ReviewException extends RuntimeException {

    private final ErrorCode errorCode;

    public ReviewException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ReviewException(String message) {
        super(message);
        this.errorCode = ErrorCode.BAD_REQUEST;
    }
}
