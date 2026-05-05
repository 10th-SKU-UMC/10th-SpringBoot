package com.example.umc10th.mission.repository;

import com.example.umc10th.mission.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {
}

