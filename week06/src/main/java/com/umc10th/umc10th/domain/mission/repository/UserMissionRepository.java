package com.umc10th.umc10th.domain.mission.repository;

import com.umc10th.umc10th.domain.mission.entity.mapping.UserMission;
import com.umc10th.umc10th.domain.mission.enums.MissionStatus;
import com.umc10th.umc10th.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserMissionRepository extends JpaRepository<UserMission, Long> {


    @Query("select um from UserMission um" +
            " join fetch um.mission m" +
            " join fetch m.store s" +
            " where um.user = :user and um.missionStatus = :missionStatus")
    Page<UserMission> findAllByUserAndStatus(User user, MissionStatus missionStatus, Pageable pageable);
}
