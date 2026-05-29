package com.example.week09.domain.mission.converter;

import com.example.week09.domain.mission.dto.MissionReqDTO;
import com.example.week09.domain.mission.dto.MissionResDTO;
import com.example.week09.domain.mission.entity.Mission;
import com.example.week09.domain.mission.entity.Store;
import com.example.week09.domain.mission.entity.mapping.MemberMission;
import org.springframework.data.domain.Page;

import java.util.List;

public class MissionConverter {

    // MemberMission 엔티티 하나를 응답 DTO로 변환
    public static MissionResDTO.MissionInfo toMissionInfo(MemberMission memberMission) {
        return MissionResDTO.MissionInfo.builder()
                .missionId(memberMission.getMission().getId())                  // 미션 ID
                .storeName(memberMission.getMission().getStore().getName())      // 가게 이름
                .conditional(memberMission.getMission().getConditional())        // 미션 내용
                .point(memberMission.getMission().getPoint())                    // 포인트
                .deadline(memberMission.getMission().getDeadline().toString())   // 마감일
                .status(memberMission.getIsComplete() ? "completed" : "ongoing") // 완료 여부
                .build();
    }

    // 요청 DTO와 가게 엔티티로 Mission 엔티티 생성
    public static Mission toMission(Store store, MissionReqDTO.CreateMission dto) {
        return Mission.builder()
                .store(store)                 // 연결된 가게
                .conditional(dto.conditional()) // 미션 조건
                .point(dto.point())           // 포인트
                .deadline(dto.deadline())     // 미션 기한
                .build();
    }

    // 가게 내 미션 조회 - Mission 엔티티 하나를 응답 DTO로 변환
    public static MissionResDTO.GetMission toGetMission(Mission mission) {
        return MissionResDTO.GetMission.builder()
                .missionId(mission.getId())            // 미션 ID
                .point(mission.getPoint())             // 포인트
                .conditional(mission.getConditional()) // 미션 조건
                .build();
    }

    // MemberMission 페이징 결과를 응답 DTO로 변환
    public static MissionResDTO.MissionListRes toMissionListRes(Page<MemberMission> page) {
        List<MissionResDTO.MissionInfo> missions = page.getContent()
                .stream()
                .map(MissionConverter::toMissionInfo)
                .toList();

        return MissionResDTO.MissionListRes.builder()
                .missions(missions)
                .totalCount((int) page.getTotalElements()) // 전체 미션 수
                .hasNext(page.hasNext())                   // 다음 페이지 존재 여부
                .build();
    }
}
