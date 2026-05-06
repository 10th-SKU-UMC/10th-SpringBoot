package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.MemberMission;
import com.example.umc10th.domain.mission.entity.Mission;

import java.util.List;
import java.util.stream.Collectors;

public class MissionConverter {

    // 미션 목록 가져오기 (하나씩)
    public static MissionResDTO.ShowMissionResDTO toShowMissionResDTO(Mission mission){
        return new MissionResDTO.ShowMissionResDTO(
                mission.getId(),
                mission.getPoint(),
                mission.getDeadline(),
                mission.getStoreId()
        );
    }

    // 미션 목록 가져오기 (리스트)
    public static MissionResDTO.ShowMissionListResDTO toShowMissionListResDTO(List<Mission> missionList){
        // Mission객체를 하나씩 꺼내서 toShowMissionResDTO 메서드를 통해 DTO로 변환, 그 DTO들을 리스트로 모은다.
        List<MissionResDTO.ShowMissionResDTO> showMissionResDTOList = missionList.stream().map(MissionConverter::toShowMissionResDTO).collect(Collectors.toList());
        return new MissionResDTO.ShowMissionListResDTO(showMissionResDTOList);
    }
    
    // 유저의 미션 가져오기
    public static MissionResDTO.ShowMissionListResDTO toGetUserMissionListResDTO(List<MemberMission> memberMissionList){
        List<MissionResDTO.ShowMissionResDTO> MissionDTOList = memberMissionList.stream()
                .map(memberMission -> new MissionResDTO.ShowMissionResDTO(
                        memberMission.getMissionId(),
                        0, null, null
        )).collect(Collectors.toList());
        return new MissionResDTO.ShowMissionListResDTO(MissionDTOList);
    }
}
