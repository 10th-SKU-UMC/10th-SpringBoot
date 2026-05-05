package com.umc10th.umc10th.domain.mission.converter;

import com.umc10th.umc10th.domain.mission.dto.MissionResDto;
import com.umc10th.umc10th.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class MissionConverter {


    public static MissionResDto.MissionListDto toMissionListDto(Page<Mission> missionPage) {

        // 1. Page 객체에서 실제 엔티티 리스트를 꺼냅니다.
        List<Mission> missions = missionPage.getContent();

        // 2. 엔티티 리스트를 DTO 리스트로 변환합니다.
        List<MissionResDto.MissionDetailDto> missionDetailDtoList = missions.stream()
                .map(mission -> {
                    // 변환 로직을 명시적으로 호출 (메서드 참조 대신 람다 사용)
                    return MissionConverter.toMissionDetailDto(mission);
                })
                .collect(Collectors.toList());


        return MissionResDto.MissionListDto.builder()
                .missionList(missionDetailDtoList) // 변환된 리스트
                .listSize(missionDetailDtoList.size()) // 현재 페이지의 데이터 개수
                .totalPage(missionPage.getTotalPages()) // 전체 페이지 수
                .totalElements(missionPage.getTotalElements()) // 전체 데이터 개수
                .isFirst(missionPage.isFirst()) // 첫 페이지 여부
                .isLast(missionPage.isLast())   // 마지막 페이지 여부
                .build();
    }

    private static MissionResDto.MissionDetailDto toMissionDetailDto(Mission mission) {
        return MissionResDto.MissionDetailDto.builder()
                .missionId(mission.getId())
                .storeName(mission.getStore().getName()) // Store 엔티티 참조
                .reward(mission.getReward())             // 미션 포인트
                .deadline(mission.getDeadline())        // 마감 기한
                .missionSpec(mission.getContent())      // 미션 상세 내용
                .status(null) // 홈 화면(도전 전)이라면 null 또는 특정 기본값 설정
                .build();
    }
}
