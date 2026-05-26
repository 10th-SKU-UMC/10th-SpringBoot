package com.example.week08.domain.mission.exception.code;

import com.example.week08.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionSuccessCode implements BaseSuccessCode {

    CREATE_STORE_MISSION_OK(HttpStatus.OK, "MISSION200_1", "성공적으로 미션을 생성했습니다."),
    GET_STORE_MISSIONS_OK(HttpStatus.OK, "MISSION200_2", "성공적으로 미션을 조회했습니다."),
    GET_MISSIONS_OK(HttpStatus.OK, "MISSION200_3", "미션 목록 조회에 성공했습니다."),
    MISSION_SUCCESS_OK(HttpStatus.OK, "MISSION200_4", "미션을 성공 처리했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
