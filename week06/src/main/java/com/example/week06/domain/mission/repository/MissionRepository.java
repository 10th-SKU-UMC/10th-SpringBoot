package com.example.week06.domain.mission.repository;

import com.example.week06.domain.mission.entity.Mission;
import com.example.week06.global.enums.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    // 특정 지역에서 해당 회원이 아직 도전하지 않은 미션 목록 페이징 조회
    @Query("""
            SELECT m FROM Mission m
            WHERE m.store.location.name = :address
            AND m.id NOT IN (
                SELECT mm.mission.id FROM MemberMission mm WHERE mm.member.id = :memberId
            )
            """)
    Page<Mission> findAvailableMissionsByAddress(
            @Param("address") Address address,
            @Param("memberId") Long memberId,
            Pageable pageable
    );
}
