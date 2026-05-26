package com.umc10th.umc10th.domain.user.exception;

import com.umc10th.umc10th.global.apiPayload.code.BaseErrorCode;
import com.umc10th.umc10th.global.apiPayload.exception.ProjectException;

public class UserException extends ProjectException {

    public UserException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
