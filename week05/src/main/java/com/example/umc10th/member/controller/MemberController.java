package com.example.umc10th.member.controller;

import com.example.umc10th.global.response.ApiResponse;
import com.example.umc10th.member.dto.MemberResDTO;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

	@GetMapping("/users/{userId}/home")
	public ApiResponse<MemberResDTO.HomeResponse> getHome(@PathVariable Long userId) {
		List<MemberResDTO.HomeMissionSummary> missions = List.of(
				new MemberResDTO.HomeMissionSummary(1L, "치킨집 미션", "PROGRESS"),
				new MemberResDTO.HomeMissionSummary(2L, "카페 미션", "COMPLETE")
		);

		MemberResDTO.HomeResponse response = new MemberResDTO.HomeResponse(
				userId,
				"hong",
				missions
		);

		return ApiResponse.onSuccess("HOME200", "홈 조회 성공", response);
	}
}

