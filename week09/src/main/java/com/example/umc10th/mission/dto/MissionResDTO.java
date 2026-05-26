package com.example.umc10th.mission.dto;

import java.time.LocalDateTime;
import java.util.List;

public class MissionResDTO {

    public record UserMissionItem(
            Long missionId,
            String title,
            String status
    ) {}

    public record MissionCompleteResponse(
            Long userId,
            Long missionId,
            String status,
            LocalDateTime completedAt
    ) {}

    public record ProceedingMissionPage(
            List<UserMissionItem> missions,
            int page,
            int size,
            long totalElements,
            int totalPages,
            boolean hasNext
    ) {}
}
