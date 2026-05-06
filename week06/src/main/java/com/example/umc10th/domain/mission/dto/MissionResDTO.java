package com.example.umc10th.domain.mission.dto;

import java.time.LocalDate;
import java.util.List;

public class MissionResDTO {
    
    // 미션 목록 응답
    public record ShowMissionResDTO(
            Long missionId,
            Integer point,
            LocalDate deadline,
            Long storeId
    ){}

    // 미션 목록 응답(리스트)
    public record ShowMissionListResDTO(
            List<ShowMissionResDTO> missionList
    ){}
}
