package com.example.week09.domain.member.exception;

import com.example.week09.global.apiPayload.code.BaseErrorCode;
import com.example.week09.global.apiPayload.exception.ProjectException;

// ProjectException을 상속 → Member 관련 예외임을 구분하기 위해 따로 만든 클래스
public class MemberException extends ProjectException {

    public MemberException(BaseErrorCode errorCode) {
        super(errorCode); // 부모인 ProjectException에 에러 코드 전달
    }
}
