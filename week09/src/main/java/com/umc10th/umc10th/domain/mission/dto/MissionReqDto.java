package com.umc10th.umc10th.domain.mission.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MissionReqDto {

    //가게 미션 생성
    public record CreateMission(
            @NotNull(message = "마감 기한은 필수입니다.")
            @Future(message = "마감 기한은 현재 시간 이후여야 합니다.")
            LocalDateTime deadline,
            @NotNull(message = "미션 성공포인트는 필수입니다.")
            @Positive(message = "포인트는 1 이상이어야 합니다.")
            Integer point,
            @NotBlank(message = "미션 성공 조건(내용)은 빈칸일 수 없습니다.")
            String content
    ) {}

    // 진행중인 미션 조회 요청 (Request Body)
    public record GetMyMissions(
            @NotNull Long userId,
            @NotNull Integer page
    ) {}

    public record MissionListDto(
            List<MissionDetailDto> missionList
    ) {}

    public record MissionDetailDto(
            Long missionId,
            String storeName,
            int reward,
            LocalDate deadline,
            String missionSpec
    ) {}
}
