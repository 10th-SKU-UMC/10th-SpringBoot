package com.example.week06.domain.mission.service;

import com.example.week06.domain.mission.converter.MissionConverter;
import com.example.week06.domain.mission.dto.MissionResDTO;
import com.example.week06.domain.mission.entity.mapping.MemberMission;
import com.example.week06.domain.mission.repository.MemberMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MemberMissionRepository memberMissionRepository;

    // 내 미션 목록 조회 (페이징)
    // status: "ongoing"(진행중) 또는 "completed"(완료)
    public MissionResDTO.MissionListRes getMissions(Long memberId, String status, int page, int size) {
        // "ongoing" → false, "completed" → true 로 변환
        Boolean isComplete = status.equals("completed");

        Page<MemberMission> missionPage = memberMissionRepository.findByMemberIdAndIsComplete(
                memberId,
                isComplete,
                PageRequest.of(page, size)
        );

        return MissionConverter.toMissionListRes(missionPage);
    }

    // 미션 성공 처리 - 다음 주차에 구현 예정
    public MissionResDTO.MissionSuccessRes completeMission(Long memberId, Long missionId) {
        return null;
    }
}
