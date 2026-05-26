package com.example.umc10th.domain.mission.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

@Getter
@RequiredArgsConstructor
public enum MissionErrorCode implements BaseErrorCode {

    QUERY_NOT_VALID(HttpStatus.BAD_REQUEST,
            "MISSION400_1",
            "미션 쿼리 파라미터가 유호하지 않습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
