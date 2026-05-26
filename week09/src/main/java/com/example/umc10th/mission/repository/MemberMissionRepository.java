package com.example.umc10th.mission.repository;

import com.example.umc10th.mission.entity.mapping.MemberMission;
import com.example.umc10th.mission.enums.MissionStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    List<MemberMission> findAllByStatus(MissionStatus status);

    Optional<MemberMission> findByMemberIdAndMissionId(Long memberId, Long missionId);

    List<MemberMission> findAllByMemberId(Long memberId);

    List<MemberMission> findAllByMemberIdAndStatus(Long memberId, MissionStatus status);

    Page<MemberMission> findAllByMemberIdAndStatus(Long memberId, MissionStatus status, Pageable pageable);
}
