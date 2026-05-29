package com.example.week09.domain.mission.service;

import com.example.week09.domain.mission.converter.MissionConverter;
import com.example.week09.domain.mission.dto.MissionReqDTO;
import com.example.week09.domain.mission.dto.MissionResDTO;
import com.example.week09.domain.mission.entity.Mission;
import com.example.week09.domain.mission.entity.Store;
import com.example.week09.domain.mission.entity.mapping.MemberMission;
import com.example.week09.domain.mission.exception.StoreException;
import com.example.week09.domain.mission.exception.code.MissionErrorCode;
import com.example.week09.domain.mission.exception.code.StoreErrorCode;
import com.example.week09.domain.mission.repository.MemberMissionRepository;
import com.example.week09.domain.mission.repository.MissionRepository;
import com.example.week09.domain.mission.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MemberMissionRepository memberMissionRepository;
    private final MissionRepository missionRepository;
    private final StoreRepository storeRepository;

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

    // 가게 미션 생성
    @Transactional
    public Void createMission(Long storeId, MissionReqDTO.CreateMission dto) {
        // 가게 찾기
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(StoreErrorCode.NOT_FOUND));

        // 미션 생성
        Mission mission = MissionConverter.toMission(store, dto);

        // 미션 DB 저장
        missionRepository.save(mission);
        return null;
    }

    // 가게 내 미션들 조회
    public List<MissionResDTO.GetMission> getMissions(Long storeId) {
        // 가게 내 미션들 조회
        List<Mission> missionList = missionRepository.findAllByStore_Id(storeId);

        // 미션들 응답 DTO로 포장하기
        return missionList.stream()
                .map(MissionConverter::toGetMission)
                .toList();
    }
}
