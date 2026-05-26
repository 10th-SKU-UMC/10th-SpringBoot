package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.MemberMission;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    // 유저의 미션 목록 검색용
    List<MemberMission> findAllByMemberIdAndIsComplete(Long userId, Boolean isComplete);

    Page<MemberMission> findAllByMemberIdAndIsComplete(@NotNull(message = "검색을 위한 유저 아이디는 필수") Long aLong, Boolean isComplete, PageRequest pageRequest);
}
