package com.example.week05.domain.mission.exception.code;

import com.example.week05.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionSuccessCode implements BaseSuccessCode {

    GET_MISSIONS_OK(HttpStatus.OK, "MISSION200_1", "미션 목록 조회에 성공했습니다."),
    MISSION_SUCCESS_OK(HttpStatus.OK, "MISSION200_2", "미션을 성공 처리했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
