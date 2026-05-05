package com.example.umc10th.mission.controller;

import com.example.umc10th.global.response.ApiResponse;
import com.example.umc10th.mission.dto.MissionReqDTO;
import com.example.umc10th.mission.dto.MissionResDTO;
import com.example.umc10th.mission.exception.MissionException;
import com.example.umc10th.mission.exception.code.MissionErrorCode;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MissionController {

	@GetMapping("/users/missions")
	public ApiResponse<List<MissionResDTO.UserMissionItem>> getUserMissions(
			@RequestParam(defaultValue = "PROGRESS") String status
	) {
		List<MissionResDTO.UserMissionItem> result = List.of(
				new MissionResDTO.UserMissionItem(1L, "첫 주문 미션", status.toUpperCase()),
				new MissionResDTO.UserMissionItem(2L, "리뷰 작성 미션", status.toUpperCase())
		);

		return ApiResponse.onSuccess("MISSION200", "미션 목록 조회 성공", result);
	}

	@PatchMapping("/users/{userId}/missions/{missionId}/complete")
	public ApiResponse<MissionResDTO.MissionCompleteResponse> completeMission(
			@PathVariable Long userId,
			@PathVariable Long missionId,
			@RequestBody(required = false) MissionReqDTO.MissionCompleteRequest request
	) {
		if (request != null && request.status() != null && !"COMPLETE".equalsIgnoreCase(request.status())) {
			throw new MissionException(MissionErrorCode.MISSION_BAD_REQUEST);
		}

		MissionResDTO.MissionCompleteResponse result = new MissionResDTO.MissionCompleteResponse(
				userId,
				missionId,
				"COMPLETE",
				LocalDateTime.now()
		);

		return ApiResponse.onSuccess("MISSION200", "미션 완료 처리 성공", result);
	}
}

