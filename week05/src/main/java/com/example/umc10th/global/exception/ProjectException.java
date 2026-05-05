package com.example.umc10th.global.exception;

public class ProjectException extends RuntimeException {

    private final ErrorCode errorCode;

    public ProjectException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}

