package com.example.umc10th.domain.mission.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class MissionResDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionProgressDTO {
        private long completedCount;
        private int totalGoal;
        private int goalRewardPoint;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionItemDTO {
        private Long memberMissionId;
        private Long missionId;
        private String storeName;
        private String storeCategory;
        private Integer rewardPoint;
        private String missionCondition;
        private Integer daysLeft;
        private String status;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime startedAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionListDTO {
        private List<MissionItemDTO> missions;
        private long totalCount;
        private int currentPage;
        private boolean hasNext;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HomeMissionDTO {
        private MissionProgressDTO progress;
        private List<MissionItemDTO> myMissions;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StartMissionResultDTO {
        private Long memberMissionId;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime startedAt;
    }
}
