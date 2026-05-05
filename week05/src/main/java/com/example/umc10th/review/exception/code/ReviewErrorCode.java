package com.example.umc10th.review.exception.code;

import com.example.umc10th.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ReviewErrorCode implements ErrorCode {
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW404", "리뷰를 찾을 수 없습니다."),
    REVIEW_BAD_REQUEST(HttpStatus.BAD_REQUEST, "REVIEW400", "잘못된 리뷰 요청입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ReviewErrorCode(HttpStatus httpStatus, String code, String message) {
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

