package com.example.umc10th.domain.mission.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class MissionReqDTO {

    @Getter
    public static class StartMissionDTO {
        @NotNull(message = "미션 ID는 필수입니다.")
        private Long missionId;
    }

    @Getter
    public static class MyInProgressDTO {
        @NotNull(message = "사용자 ID는 필수입니다.")
        private Long memberId;

        @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다.")
        private int page = 0;

        @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.")
        @Max(value = 100, message = "페이지 크기는 100 이하이어야 합니다.")
        private int size = 10;
    }
}
