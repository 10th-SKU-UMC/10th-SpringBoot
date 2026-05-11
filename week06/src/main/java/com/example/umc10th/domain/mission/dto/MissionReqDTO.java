package com.example.umc10th.domain.mission.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class MissionReqDTO {

    @Getter
    public static class StartMissionDTO {
        @NotNull(message = "미션 ID는 필수입니다.")
        private Long missionId;
    }
}
