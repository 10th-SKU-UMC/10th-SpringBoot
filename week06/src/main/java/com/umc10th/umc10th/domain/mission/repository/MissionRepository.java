package com.umc10th.umc10th.domain.mission.repository;

import com.umc10th.umc10th.domain.mission.entity.Mission;
import com.umc10th.umc10th.domain.mission.entity.mapping.UserMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Query("SELECT m FROM Mission m " +
            "JOIN FETCH m.store s " +
            "WHERE s.region.name = :regionName " +
            "AND m.id NOT IN (SELECT um.mission.id FROM UserMission um WHERE um.user.id = :userId )")
    Page<Mission> findAllByRegionAndNotChallenged(Long userId, String regionName, Pageable pageable);



}
