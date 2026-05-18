package com.example.week06.domain.mission.exception.code;

import com.example.week06.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionErrorCode implements BaseErrorCode {

    // Mission을 DB에서 찾지 못했을 때 사용하는 에러 코드
    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "MISSION404_1", "해당 미션을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
