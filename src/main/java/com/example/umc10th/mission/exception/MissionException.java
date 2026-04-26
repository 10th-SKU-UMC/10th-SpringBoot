package com.example.umc10th.mission.exception;

import com.example.umc10th.mission.exception.code.MissionErrorCode;

public class MissionException extends RuntimeException {

    private final MissionErrorCode errorCode;

    public MissionException(MissionErrorCode errorCode) {
        super(errorCode.name());
        this.errorCode = errorCode;
    }

    public MissionErrorCode getErrorCode() {
        return errorCode;
    }
}

