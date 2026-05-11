package com.example.umc10th.domain.mission.exception;

import com.example.umc10th.global.apiPayload.code.ErrorCode;
import lombok.Getter;

@Getter
public class MissionException extends RuntimeException {

    private final ErrorCode errorCode;

    public MissionException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public MissionException(String message) {
        super(message);
        this.errorCode = ErrorCode.BAD_REQUEST;
    }
}
