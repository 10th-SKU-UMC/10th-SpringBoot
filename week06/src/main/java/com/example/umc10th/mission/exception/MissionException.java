package com.example.umc10th.mission.exception;

import com.example.umc10th.global.exception.ProjectException;
import com.example.umc10th.mission.exception.code.MissionErrorCode;

public class MissionException extends ProjectException {
    public MissionException(MissionErrorCode errorCode) {
        super(errorCode);
    }
}

