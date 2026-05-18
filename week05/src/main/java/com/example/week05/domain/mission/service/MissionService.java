package com.example.week05.domain.mission.service;

import com.example.week05.domain.mission.dto.MissionResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MissionService {

    // 미션 목록 조회 - 다음 주차에 구현 예정
    public MissionResDTO.MissionListRes getMissions(Long memberId, String status) {
        return null;
    }

    // 미션 성공 처리 - 다음 주차에 구현 예정
    public MissionResDTO.MissionSuccessRes completeMission(Long memberId, Long missionId) {
        return null;
    }
}
