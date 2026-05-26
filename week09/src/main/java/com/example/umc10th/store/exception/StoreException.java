package com.example.umc10th.store.exception;

import com.example.umc10th.global.exception.ProjectException;
import com.example.umc10th.store.exception.code.StoreErrorCode;

public class StoreException extends ProjectException {
    public StoreException(StoreErrorCode errorCode) {
        super(errorCode);
    }
}

