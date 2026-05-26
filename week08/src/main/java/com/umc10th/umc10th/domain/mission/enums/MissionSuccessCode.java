package com.umc10th.umc10th.domain.mission.enums;

import com.umc10th.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionSuccessCode implements BaseSuccessCode {

    CREATED(HttpStatus.OK, "Mission200_1", "성공적으로 미션을 생성했습니다."),
    OK(HttpStatus.OK, "Mission200_2", "성공적으로 미션을 조회했습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;
}
