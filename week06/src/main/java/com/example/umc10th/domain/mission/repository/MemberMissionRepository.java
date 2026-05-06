package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.MemberMission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    // 유저의 미션 목록 검색용
    List<MemberMission> findAllByUserIdAndIsComplete(Long userId, Boolean isComplete);
}
