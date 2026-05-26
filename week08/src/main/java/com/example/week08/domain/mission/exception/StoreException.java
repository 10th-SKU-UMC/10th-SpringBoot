package com.example.week08.domain.mission.exception;

import com.example.week08.global.apiPayload.code.BaseErrorCode;
import com.example.week08.global.apiPayload.exception.ProjectException;

// ProjectException을 상속 → Store 관련 예외임을 구분하기 위해 따로 만든 클래스
public class StoreException extends ProjectException {

    public StoreException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
