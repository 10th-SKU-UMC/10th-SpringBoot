package com.example.umc10th.review.exception;

import com.example.umc10th.review.exception.code.ReviewErrorCode;

public class ReviewException extends RuntimeException {

    private final ReviewErrorCode errorCode;

    public ReviewException(ReviewErrorCode errorCode) {
        super(errorCode.name());
        this.errorCode = errorCode;
    }

    public ReviewErrorCode getErrorCode() {
        return errorCode;
    }
}

