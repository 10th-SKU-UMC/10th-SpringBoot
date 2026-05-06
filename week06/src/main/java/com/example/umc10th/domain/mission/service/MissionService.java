package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.controller.MissionController;
import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.MemberMission;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;
    private final MemberMissionRepository memberMissionRepository;

    // 미션 목록 가져오기 로직
    @Transactional
    public MissionResDTO.ShowMissionListResDTO getMissionList(Long locationId){
        // 임시로 일단 전부 가져오는 로직
        List<Mission> missionList = missionRepository.findAll();
        return MissionConverter.toShowMissionListResDTO(missionList);
    }

    // 미션 성공 누르기
    @Transactional
    public void completeMission(Long userMissionId){
        MemberMission memberMission = memberMissionRepository.findById(userMissionId)
                .orElseThrow(() -> new RuntimeException("해당 유저 미션이 존재하지 않습니다"));
        memberMission.complete();
    }

    // 사용자 미션 조회
    @Transactional
    public MissionResDTO.ShowMissionListResDTO getUserMissionList(Long userId, Integer isCompleteInt){
        Boolean isComplete = (isCompleteInt==1);
        List<MemberMission> memberMissionList = memberMissionRepository.findAllByUserIdAndIsComplete(userId, isComplete);
        return MissionConverter.toGetUserMissionListResDTO(memberMissionList);
    }
}
