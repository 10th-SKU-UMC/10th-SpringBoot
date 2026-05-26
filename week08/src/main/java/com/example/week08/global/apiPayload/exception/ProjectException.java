package com.example.week08.global.apiPayload.exception;

import com.example.week08.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;

@Getter
public class ProjectException extends RuntimeException {

    // 어떤 에러인지 담아두는 변수 (예: NOT_FOUND, BAD_REQUEST 등)
    private final BaseErrorCode errorCode;

    // 에러 코드를 받아서 저장하는 생성자
    public ProjectException(BaseErrorCode errorCode) {
        super(errorCode.getMessage()); // RuntimeException에 메시지 전달
        this.errorCode = errorCode;
    }
}
