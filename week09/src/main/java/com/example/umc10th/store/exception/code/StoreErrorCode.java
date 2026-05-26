package com.example.umc10th.store.exception.code;

import com.example.umc10th.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum StoreErrorCode implements ErrorCode {
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "STORE404", "가게를 찾을 수 없습니다."),
    STORE_BAD_REQUEST(HttpStatus.BAD_REQUEST, "STORE400", "잘못된 가게 요청입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    StoreErrorCode(HttpStatus httpStatus, String code, String message) {
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

