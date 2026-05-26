package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.MemberMission;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.store.entity.Store;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public class MissionConverter {

    // 미션 목록 가져오기 (하나씩)
    public static MissionResDTO.ShowMissionResDTO toShowMissionResDTO(Mission mission){
        return new MissionResDTO.ShowMissionResDTO(
                mission.getId(),
                mission.getPoint(),
                mission.getDeadline(),
                mission.getStore().getId()
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
                        memberMission.getMission().getId(),
                        0, null, null
        )).collect(Collectors.toList());
        return new MissionResDTO.ShowMissionListResDTO(MissionDTOList);
    }

    // 가게 미션 생성
    public static Mission toCreateMission(
            Store store,
            MissionReqDTO.CreateMissionReqDTO dto
    ){
        return Mission.builder()
                .store(store)
                .point(dto.point())
                .deadline(dto.deadline())
                .build();
    }

    // 가게 내 미션 조회
    public static MissionResDTO.GetMissionResDTO toGetStoreMission(
            Mission mission
    ){
        return MissionResDTO.GetMissionResDTO.builder()
                .point(mission.getPoint())
                .missionId(mission.getId())
                .conditional(mission.getConditional())
                .build();
    }

    // 페이지네이션 툴 생성
    public static <T> MissionResDTO.Pagination<T> toPagination(
            List<T> data,
            //Integer pageNumber,
            Boolean hasNext,
            String nextCursor,
            Integer pageSize
    ){
        return MissionResDTO.Pagination.<T>builder()
                .data(data)
                //.pageNumber(pageNumber)
                .hasNext(hasNext)
                .nextCursor(nextCursor)
                .pageSize(pageSize)
                .build();
    }

    // 나의 미션 목록 조회
    public static MissionResDTO.GetMemberMissionResDTO toGetMemberMission(
            MemberMission memberMission
    ){
        return MissionResDTO.GetMemberMissionResDTO.builder()
                .memberMissionId(memberMission.getId())
                .storeName(memberMission.getMission().getStore().getName())
                .conditional(memberMission.getMission().getConditional())
                .point(memberMission.getMission().getPoint())
                .build();
    }
}
