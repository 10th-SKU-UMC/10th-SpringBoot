package com.example.umc10th.mission.exception.code;

import com.example.umc10th.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum MissionErrorCode implements ErrorCode {
    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "MISSION404", "미션을 찾을 수 없습니다."),
    MISSION_BAD_REQUEST(HttpStatus.BAD_REQUEST, "MISSION400", "잘못된 미션 요청입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    MissionErrorCode(HttpStatus httpStatus, String code, String message) {
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

