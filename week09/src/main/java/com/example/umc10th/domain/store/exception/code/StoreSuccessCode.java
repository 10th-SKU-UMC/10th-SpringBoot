package com.example.umc10th.domain.store.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StoreSuccessCode implements BaseSuccessCode {

    OK_CREATE_STORE(HttpStatus.OK,
            "STORE200_1",
            "Store 생성 요청 성공"),
    OK_CREATE_LOCATION(HttpStatus.OK,
            "LOCATION200_1",
            "Location 생성 요청 성공"),
    OK_CREATE_REVIEW(HttpStatus.OK,
            "REVIEW200_1",
            "Review 생성 요청 성공")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}