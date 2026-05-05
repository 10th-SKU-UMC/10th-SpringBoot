package com.umc10th.umc10th.domain.mission.dto;

import com.umc10th.umc10th.domain.mission.enums.MissionStatus;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MissionResDto {

    // 1. 미션 상세 정보 (카드 UI 데이터)
    @Builder
    public record MissionDetailDto(
            Long missionId,
            String storeName,
            int reward,
            LocalDate deadline,
            String missionSpec,
            MissionStatus status
    ) {}

    // 2. 미션 목록 응답 (페이징 포함)
    @Builder
    public record MissionListDto(
            List<MissionDetailDto> missionList,
            Integer listSize,
            Integer totalPage,
            Long totalElements,
            Boolean isFirst,
            Boolean isLast
    ) {}

    // 3. 미션 화면 메인 응답 (와이어프레임의 7/10, 리스트 포함)
    // 닉네임을 제거하고 UI에 필요한 포인트와 진행률에 집중했습니다.
    @Builder
    public record MissionViewDto(
            Integer totalPoint,        // 우측 상단 포인트
            Integer currentSuccess,    // 현재 성공 개수 (7)
            Integer goalCount,         // 목표 개수 (10)
            MissionListDto missionList  // 하단 미션 목록
    ) {}

    // 4. 미션 도전 결과 응답
    @Builder
    public record MissionActionDto(
            Long memberMissionId,
            MissionStatus status,
            LocalDateTime updatedAt
    ) {}

    // 5. 미션 성공 처리 응답
    @Builder
    public record CompleteResultDto(
            Long memberMissionId,
            LocalDateTime completedAt
    ) {}
}