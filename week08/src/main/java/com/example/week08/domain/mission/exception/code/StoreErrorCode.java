package com.example.week08.domain.mission.exception.code;

import com.example.week08.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StoreErrorCode implements BaseErrorCode {

    // Store를 DB에서 찾지 못했을 때 사용하는 에러 코드
    NOT_FOUND(HttpStatus.NOT_FOUND, "STORE404_1", "해당 가게가 존재하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
