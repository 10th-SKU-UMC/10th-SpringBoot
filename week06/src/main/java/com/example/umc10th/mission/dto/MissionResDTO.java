package com.example.umc10th.mission.dto;

import java.time.LocalDateTime;

public class MissionResDTO {

	public record UserMissionItem(
			Long missionId,
			String title,
			String status
	) {
	}

	public record MissionCompleteResponse(
			Long userId,
			Long missionId,
			String status,
			LocalDateTime completedAt
	) {
	}
}

