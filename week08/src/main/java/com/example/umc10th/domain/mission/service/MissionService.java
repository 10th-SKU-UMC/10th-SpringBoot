package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exceptions.MemberException;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.enums.MissionStatus;
import com.example.umc10th.domain.mission.exception.MissionException;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import com.example.umc10th.global.apiPayload.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private static final int HOME_MISSION_GOAL = 10;
    private static final int HOME_MISSION_GOAL_REWARD = 1_000;

    private final MemberRepository memberRepository;
    private final MissionRepository missionRepository;
    private final MemberMissionRepository memberMissionRepository;

    public MissionResDTO.HomeMissionDTO getHomeMissions(Long memberId) {
        Member member = findMemberById(memberId);
        long completedCount = memberMissionRepository.countCompletedByMemberId(memberId);

        MissionResDTO.MissionProgressDTO progress = MissionResDTO.MissionProgressDTO.builder()
                .completedCount(completedCount)
                .totalGoal(HOME_MISSION_GOAL)
                .goalRewardPoint(HOME_MISSION_GOAL_REWARD)
                .build();

        Pageable pageable = PageRequest.of(0, 5, Sort.by("createdAt").descending());
        Page<MemberMission> page = memberMissionRepository.findByMemberIdAndStatus(
                memberId, MissionStatus.IN_PROGRESS, pageable);
        List<MemberMission> myMissions = page.getContent();

        return MissionConverter.toHomeMissionDTO(progress, myMissions);
    }

    public MissionResDTO.MissionListDTO getMyMissions(Long memberId, MissionStatus status,
                                                      int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<MemberMission> result = memberMissionRepository.findByMemberIdAndStatus(
                memberId, status, pageable);
        return MissionConverter.toMissionListDTO(result);
    }

    public MissionResDTO.MissionListDTO getMyInProgressMissions(MissionReqDTO.MyInProgressDTO request) {
        findMemberById(request.getMemberId());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(),
                Sort.by("createdAt").descending());
        Page<MemberMission> result = memberMissionRepository.findByMemberIdAndStatus(
                request.getMemberId(), MissionStatus.IN_PROGRESS, pageable);
        return MissionConverter.toMissionListDTO(result);
    }

    @Transactional
    public MissionResDTO.StartMissionResultDTO startMission(Long memberId, Long missionId) {
        if (memberMissionRepository.existsByMemberIdAndMissionId(memberId, missionId)) {
            throw new MissionException(ErrorCode.MISSION_ALREADY_STARTED);
        }
        Member member = findMemberById(memberId);
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new MissionException(ErrorCode.MISSION_NOT_FOUND));

        MemberMission memberMission = MemberMission.builder()
                .member(member)
                .mission(mission)
                .status(MissionStatus.IN_PROGRESS)
                .build();
        MemberMission saved = memberMissionRepository.save(memberMission);
        return MissionConverter.toStartMissionResultDTO(saved);
    }

    @Transactional
    public void completeMission(Long memberId, Long memberMissionId) {
        MemberMission memberMission = memberMissionRepository.findById(memberMissionId)
                .orElseThrow(() -> new MissionException(ErrorCode.MISSION_NOT_FOUND));
        if (!memberMission.getMember().getId().equals(memberId)) {
            throw new MissionException(ErrorCode.FORBIDDEN);
        }
        if (memberMission.getStatus() != MissionStatus.IN_PROGRESS) {
            throw new MissionException(ErrorCode.MISSION_NOT_STARTED);
        }
        memberMission.complete();
        memberMission.getMember().addPoint(memberMission.getMission().getRewardPoint());
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
