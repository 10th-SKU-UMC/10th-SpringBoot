package com.example.umc10th.mission.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class MissionReqDTO {

    public record MissionCompleteRequest(String status) {}

    public record ProceedingMissionRequest(
            @NotNull(message = "회원 ID는 필수입니다.")
            Long memberId,

            @Min(value = 0, message = "페이지는 0 이상이어야 합니다.")
            int page,

            @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.")
            @Max(value = 100, message = "페이지 크기는 100 이하여야 합니다.")
            int size
    ) {}
}
