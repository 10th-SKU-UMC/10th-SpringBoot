package com.example.umc10th.domain.mission.dto;

import java.time.LocalDate;

public class MissionResDTO {
    
    // 미션 목록 응답
    public record ShowMissionListResDTO(
            Long missionId,
            Integer point,
            LocalDate deadline,
            Long storeId
    ){}
}
