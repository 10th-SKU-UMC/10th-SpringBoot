package com.example.week06.domain.review.exception;

import com.example.week06.global.apiPayload.code.BaseErrorCode;
import com.example.week06.global.apiPayload.exception.ProjectException;

// ProjectException을 상속 → Review 관련 예외임을 구분하기 위해 따로 만든 클래스
public class ReviewException extends ProjectException {

    public ReviewException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
