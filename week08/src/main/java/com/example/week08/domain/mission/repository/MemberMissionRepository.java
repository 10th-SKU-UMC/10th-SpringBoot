package com.example.week08.domain.mission.repository;

import com.example.week08.domain.mission.entity.mapping.MemberMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    // 특정 회원의 미션 목록을 완료 여부로 필터링해서 페이징 조회
    // isComplete: false = 진행중, true = 완료
    @Query("SELECT mm FROM MemberMission mm WHERE mm.member.id = :memberId AND mm.isComplete = :isComplete")
    Page<MemberMission> findByMemberIdAndIsComplete(
            @Param("memberId") Long memberId,
            @Param("isComplete") Boolean isComplete,
            Pageable pageable
    );

    // 특정 회원의 완료된 미션 수 조회 (홈 화면 진행률 표시용)
    @Query("SELECT COUNT(mm) FROM MemberMission mm WHERE mm.member.id = :memberId AND mm.isComplete = true")
    int countCompletedMissions(@Param("memberId") Long memberId);
}
