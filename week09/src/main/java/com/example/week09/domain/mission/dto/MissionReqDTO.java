package com.example.week09.domain.mission.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

public class MissionReqDTO {

    // 내 미션 목록 조회 요청 DTO
    @Builder
    public record GetMissionsReq(
            Long memberId  // 조회할 회원 ID
    ) {}

    // 가게 미션 생성 요청 DTO
    @Builder
    public record CreateMission(
            @NotNull(message = "미션 기한은 필수입니다.")
            @Future(message = "미션 기한은 오늘 이후 날짜여야 합니다.")
            LocalDate deadline,   // 미션 기한

            @NotNull(message = "포인트는 필수입니다.")
            @Min(value = 0, message = "포인트는 0 이상이어야 합니다.")
            Integer point,        // 성공 시 받을 포인트

            @NotBlank(message = "미션 조건은 필수입니다.")
            String conditional    // 미션 조건 (예: 12,000원 이상 식사)
    ) {}
}
