package com.example.week05.domain.review.exception.code;

import com.example.week05.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewSuccessCode implements BaseSuccessCode {

    CREATE_REVIEW_OK(HttpStatus.OK, "REVIEW200_1", "리뷰가 등록되었습니다."),
    GET_REVIEWS_OK(HttpStatus.OK, "REVIEW200_2", "리뷰 목록 조회에 성공했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
