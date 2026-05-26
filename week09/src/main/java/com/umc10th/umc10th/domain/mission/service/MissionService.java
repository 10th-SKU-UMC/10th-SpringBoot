package com.umc10th.umc10th.domain.mission.service;

import com.umc10th.umc10th.domain.mission.converter.MissionConverter;
import com.umc10th.umc10th.domain.mission.converter.UserMissionConverter;
import com.umc10th.umc10th.domain.mission.dto.MissionReqDto;
import com.umc10th.umc10th.domain.mission.dto.MissionResDto;
import com.umc10th.umc10th.domain.mission.entity.Mission;
import com.umc10th.umc10th.domain.mission.entity.mapping.UserMission;
import com.umc10th.umc10th.domain.mission.enums.MissionStatus;
import com.umc10th.umc10th.domain.mission.exception.code.MissionErrorCode;
import com.umc10th.umc10th.domain.mission.repository.MissionRepository;
import com.umc10th.umc10th.domain.mission.repository.UserMissionRepository;
import com.umc10th.umc10th.domain.store.entity.Store;
import com.umc10th.umc10th.domain.store.exception.StoreException;
import com.umc10th.umc10th.domain.store.exception.code.StoreErrorCode;
import com.umc10th.umc10th.domain.store.repository.StoreRepository;
import com.umc10th.umc10th.domain.user.entity.User;
import com.umc10th.umc10th.domain.user.repository.UserRepository;
import com.umc10th.umc10th.global.apiPayload.code.GeneralErrorCode;
import com.umc10th.umc10th.global.apiPayload.exception.ProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final UserMissionRepository userMissionRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final MissionRepository missionRepository;

    public MissionResDto.MissionListDto getMyMissionList(Long userId, MissionStatus missionStatus, Integer page) {

        // 해당 유저 찾기
        User user = userRepository.findById(userId).orElseThrow(() -> new ProjectException(GeneralErrorCode.NOT_FOUND));

        //유저의 미션 조회 및 DTO 변환
        Page<UserMission> userMissionPage = userMissionRepository.findAllByUserAndStatus(user, missionStatus, PageRequest.of(page, 10, Sort.by("createdAt").descending()));

        return UserMissionConverter.toMissionListDto(userMissionPage);
    }

    //가게 미션 생성

    public Void createMission(Long storeId, MissionReqDto.CreateMission dto) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(StoreErrorCode.NOT_FOUND));

        Mission mission = MissionConverter.toMission(store, dto);
        missionRepository.save(mission);

        return null;
    }
    //가게 별 미션 조회

    public MissionResDto.Pagenation<MissionResDto.GetMission> getMissions(
            Long storeId, Integer pageSize, Integer pageNumber, String sort
    ) {

        // 1. 정렬 정보 설정
        Sort sortInfo;
        if (sort != null) {
            sortInfo = Sort.by(sort);
        } else {
            sortInfo = Sort.by("id").descending();
        }

        // 2. 페이지 요청 객체 생성
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sortInfo);

        // 3. DB에서 해당 가게의 미션들을 페이지 단위로 조회
        Page<Mission> missionPage = missionRepository.findAllByStore_id(storeId, pageRequest);


        MissionResDto.Pagenation<MissionResDto.GetMission> missionPagination = MissionConverter.toPagination(
                missionPage.map(mission -> MissionConverter.toGetMission(mission)).toList(), missionPage.getNumber(), missionPage.getSize());

        return missionPagination;

    }

    // 진행중인 미션 조회 (오프셋 기반 페이지네이션)
    public MissionResDto.Pagenation<MissionResDto.GetMyChallengingDto> getMyChallengingMissions(
            MissionReqDto.GetMyMissions request) {

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.NOT_FOUND));

        Page<UserMission> page = userMissionRepository.findAllByUserAndStatus(
                user, MissionStatus.CHALLENGING,
                PageRequest.of(request.page(), 10, Sort.by("createdAt").descending()));

        return MissionConverter.toPagination(
                page.map(userMission -> UserMissionConverter.toGetMyChallengingDto(userMission)).toList(),
                page.getNumber(), page.getSize());
    }
}
