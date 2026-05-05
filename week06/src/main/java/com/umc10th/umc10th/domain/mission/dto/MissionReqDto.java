package com.umc10th.umc10th.domain.mission.dto;

import java.time.LocalDate;
import java.util.List;

public class MissionReqDto {
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
