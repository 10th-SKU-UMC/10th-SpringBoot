package com.example.umc10th.mission.converter;

import com.example.umc10th.mission.dto.MissionResDTO;
import com.example.umc10th.mission.entity.mapping.MemberMission;
import com.example.umc10th.mission.enums.MissionStatus;
import java.time.LocalDateTime;

public final class MissionConverter {

    private MissionConverter() {
    }

    public static MissionResDTO.UserMissionItem toUserMissionItem(MemberMission memberMission) {
        return new MissionResDTO.UserMissionItem(
                memberMission.getMission().getId(),
                memberMission.getMission().getName(),
                memberMission.getStatus().name()
        );
    }

    public static MissionResDTO.MissionCompleteResponse toMissionCompleteResponse(Long userId,
                                                                                  Long missionId,
                                                                                  MissionStatus status,
                                                                                  LocalDateTime completedAt) {
        return new MissionResDTO.MissionCompleteResponse(
                userId,
                missionId,
                status.name(),
                completedAt
        );
    }
}
