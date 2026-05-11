package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class MissionConverter {

    public static MissionResDTO.MissionItemDTO toMissionItemDTO(MemberMission memberMission) {
        Mission mission = memberMission.getMission();
        int daysLeft = 0;
        if (mission.getDeadlineDate() != null) {
            daysLeft = (int) ChronoUnit.DAYS.between(LocalDate.now(), mission.getDeadlineDate());
        }
        return MissionResDTO.MissionItemDTO.builder()
                .memberMissionId(memberMission.getId())
                .missionId(mission.getId())
                .storeName(mission.getStore().getName())
                .storeCategory(mission.getStore().getCategory())
                .rewardPoint(mission.getRewardPoint())
                .missionCondition(mission.getMissionCondition())
                .daysLeft(daysLeft)
                .status(memberMission.getStatus().name())
                .startedAt(memberMission.getCreatedAt())
                .build();
    }

    public static MissionResDTO.MissionListDTO toMissionListDTO(Page<MemberMission> page) {
        List<MissionResDTO.MissionItemDTO> items = page.getContent().stream()
                .map(MissionConverter::toMissionItemDTO)
                .collect(Collectors.toList());
        return MissionResDTO.MissionListDTO.builder()
                .missions(items)
                .totalCount(page.getTotalElements())
                .currentPage(page.getNumber())
                .hasNext(page.hasNext())
                .build();
    }

    public static MissionResDTO.HomeMissionDTO toHomeMissionDTO(
            MissionResDTO.MissionProgressDTO progress,
            List<MemberMission> myMissions) {
        List<MissionResDTO.MissionItemDTO> items = myMissions.stream()
                .map(MissionConverter::toMissionItemDTO)
                .collect(Collectors.toList());
        return MissionResDTO.HomeMissionDTO.builder()
                .progress(progress)
                .myMissions(items)
                .build();
    }

    public static MissionResDTO.StartMissionResultDTO toStartMissionResultDTO(MemberMission memberMission) {
        return MissionResDTO.StartMissionResultDTO.builder()
                .memberMissionId(memberMission.getId())
                .startedAt(memberMission.getCreatedAt())
                .build();
    }
}
