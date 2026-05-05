package com.example.umc10th.mission.service;

import com.example.umc10th.member.exception.MemberException;
import com.example.umc10th.member.exception.code.MemberErrorCode;
import com.example.umc10th.member.repository.MemberRepository;
import com.example.umc10th.mission.converter.MissionConverter;
import com.example.umc10th.mission.dto.MissionReqDTO;
import com.example.umc10th.mission.dto.MissionResDTO;
import com.example.umc10th.mission.entity.mapping.MemberMission;
import com.example.umc10th.mission.enums.MissionStatus;
import com.example.umc10th.mission.exception.MissionException;
import com.example.umc10th.mission.exception.code.MissionErrorCode;
import com.example.umc10th.mission.repository.MemberMissionRepository;
import com.example.umc10th.mission.repository.MissionRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MissionService {

    private final MemberMissionRepository memberMissionRepository;
    private final MissionRepository missionRepository;
    private final MemberRepository memberRepository;

    public MissionService(MemberMissionRepository memberMissionRepository,
                          MissionRepository missionRepository,
                          MemberRepository memberRepository) {
        this.memberMissionRepository = memberMissionRepository;
        this.missionRepository = missionRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public List<MissionResDTO.UserMissionItem> getUserMissions(Long userId, String status) {
        memberRepository.findById(userId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        MissionStatus missionStatus = parseStatus(status);
        List<MemberMission> memberMissions = memberMissionRepository
                .findAllByMemberIdAndStatus(userId, missionStatus);
        return memberMissions.stream()
                .map(MissionConverter::toUserMissionItem)
                .collect(Collectors.toList());
    }

    @Transactional
    public MissionResDTO.MissionCompleteResponse completeMission(Long userId,
                                                                 Long missionId,
                                                                 MissionReqDTO.MissionCompleteRequest request) {
        memberRepository.findById(userId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        missionRepository.findById(missionId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.MISSION_NOT_FOUND));

        MemberMission memberMission = memberMissionRepository
                .findByMemberIdAndMissionId(userId, missionId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.MISSION_BAD_REQUEST));

        MissionStatus newStatus = resolveCompleteStatus(request);
        memberMission.setStatus(newStatus);

        LocalDateTime completedAt = newStatus == MissionStatus.COMPLETED ? LocalDateTime.now() : null;
        return MissionConverter.toMissionCompleteResponse(userId, missionId, newStatus, completedAt);
    }

    private MissionStatus resolveCompleteStatus(MissionReqDTO.MissionCompleteRequest request) {
        if (request == null || request.status() == null || request.status().isBlank()) {
            return MissionStatus.COMPLETED;
        }
        MissionStatus status = parseStatus(request.status());
        if (status != MissionStatus.COMPLETED) {
            throw new MissionException(MissionErrorCode.MISSION_BAD_REQUEST);
        }
        return status;
    }

    private MissionStatus parseStatus(String status) {
        if (status == null || status.isBlank()) {
            throw new MissionException(MissionErrorCode.MISSION_BAD_REQUEST);
        }
        String normalized = status.trim().toUpperCase(Locale.ROOT);
        if (normalized.equals("PROGRESS") || normalized.equals("IN_PROGRESS")) {
            return MissionStatus.PROCEEDING;
        }
        if (normalized.equals("COMPLETE")) {
            return MissionStatus.COMPLETED;
        }
        try {
            return MissionStatus.valueOf(normalized);
        } catch (IllegalArgumentException ex) {
            throw new MissionException(MissionErrorCode.MISSION_BAD_REQUEST);
        }
    }
}
