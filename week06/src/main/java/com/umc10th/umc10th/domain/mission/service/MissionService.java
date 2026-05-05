package com.umc10th.umc10th.domain.mission.service;

import com.umc10th.umc10th.domain.mission.converter.MissionConverter;
import com.umc10th.umc10th.domain.mission.converter.UserMissionConverter;
import com.umc10th.umc10th.domain.mission.dto.MissionResDto;
import com.umc10th.umc10th.domain.mission.entity.mapping.UserMission;
import com.umc10th.umc10th.domain.mission.enums.MissionStatus;
import com.umc10th.umc10th.domain.mission.repository.UserMissionRepository;
import com.umc10th.umc10th.domain.user.entity.User;
import com.umc10th.umc10th.domain.user.repository.UserRepository;
import com.umc10th.umc10th.global.apiPayload.code.GeneralErrorCode;
import com.umc10th.umc10th.global.apiPayload.exception.ProjectException;
import com.umc10th.umc10th.global.apiPayload.handler.GeneralExceptionAdvice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final UserMissionRepository userMissionRepository;
    private final UserRepository userRepository;
    public MissionResDto.MissionListDto getMyMissionList(Long userId, MissionStatus missionStatus, Integer page) {

        // 해당 유저 찾기
        User user = userRepository.findById(userId).orElseThrow(() -> new ProjectException(GeneralErrorCode.NOT_FOUND));

        //유저의 미션 조회 및 DTO 변환
        Page<UserMission> userMissionPage = userMissionRepository.findAllByUserAndStatus(user, missionStatus, PageRequest.of(page, 10, Sort.by("createdAt").descending()));

        return UserMissionConverter.toMissionListDto(userMissionPage);
    }
}
