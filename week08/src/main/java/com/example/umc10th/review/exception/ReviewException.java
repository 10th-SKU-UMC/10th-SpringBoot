package com.example.umc10th.review.exception;

import com.example.umc10th.global.exception.ProjectException;
import com.example.umc10th.review.exception.code.ReviewErrorCode;

public class ReviewException extends ProjectException {
    public ReviewException(ReviewErrorCode errorCode) {
        super(errorCode);
    }
}

