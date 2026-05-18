package com.example.week06.domain.mission.dto;

import lombok.Builder;

import java.util.List;

public class MissionResDTO {

    // 미션 하나의 정보
    @Builder
    public record MissionInfo(
            Long missionId,      // 미션 ID
            String storeName,    // 가게 이름
            String conditional,  // 미션 내용 (예: 12,000원 이상의 식사를 하세요!)
            Integer point,       // 포인트
            String deadline,     // 미션 마감일
            String status        // 미션 상태 (ongoing: 진행중 / completed: 완료)
    ) {}

    // 미션 목록 응답 데이터 (페이징 포함)
    @Builder
    public record MissionListRes(
            List<MissionInfo> missions, // 미션 목록
            Integer totalCount,         // 전체 미션 수
            Boolean hasNext             // 다음 페이지 존재 여부
    ) {}

    // 미션 성공 처리 응답 데이터
    @Builder
    public record MissionSuccessRes(
            Long missionId, // 미션 ID
            String status   // 변경된 상태 (completed)
    ) {}
}
