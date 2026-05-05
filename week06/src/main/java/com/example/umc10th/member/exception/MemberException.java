package com.example.umc10th.member.exception;

import com.example.umc10th.global.exception.ProjectException;
import com.example.umc10th.member.exception.code.MemberErrorCode;

public class MemberException extends ProjectException {
    public MemberException(MemberErrorCode errorCode) {
        super(errorCode);
    }
}

