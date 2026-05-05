package com.umc10th.umc10th.domain.mission.converter;

import com.umc10th.umc10th.domain.mission.dto.MissionResDto;
import com.umc10th.umc10th.domain.mission.entity.Mission;
import com.umc10th.umc10th.domain.mission.entity.mapping.UserMission;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class UserMissionConverter {

    // Page<UserMission> -> MissionListDto 변환
    public static MissionResDto.MissionListDto toMissionListDto(Page<UserMission> userMissionPage) {

        List<MissionResDto.MissionDetailDto> missionDetailList = userMissionPage.getContent().stream()
                .map(UserMissionConverter::toMissionDetailDto)
                .collect(Collectors.toList());

        return MissionResDto.MissionListDto.builder()
                .missionList(missionDetailList)
                .listSize(missionDetailList.size())
                .totalPage(userMissionPage.getTotalPages())
                .totalElements(userMissionPage.getTotalElements())
                .isFirst(userMissionPage.isFirst())
                .isLast(userMissionPage.isLast())
                .build();
    }

    // 개별 UserMission -> MissionDetailDto 변환
    public static MissionResDto.MissionDetailDto toMissionDetailDto(UserMission userMission) {
        Mission mission = userMission.getMission();

        return MissionResDto.MissionDetailDto.builder()
                .missionId(mission.getId())
                .storeName(mission.getStore().getName())
                .reward(mission.getReward()) // DTO의 reward에 매핑
                .deadline(mission.getDeadline()) // 엔티티에 deadline 필드가 있다고 가정
                .missionSpec(mission.getContent()) // 미션 상세 내용
                .status(userMission.getMissionStatus())
                .build();
    }
}