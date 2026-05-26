package com.umc10th.umc10th.domain.store.exception;

import com.umc10th.umc10th.global.apiPayload.code.BaseErrorCode;
import com.umc10th.umc10th.global.apiPayload.exception.ProjectException;

public class StoreException extends ProjectException {
    public StoreException(BaseErrorCode code) {
        super(code);
    }
}
