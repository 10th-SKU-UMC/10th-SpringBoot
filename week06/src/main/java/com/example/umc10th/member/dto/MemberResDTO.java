package com.example.umc10th.member.dto;

import java.util.List;

public class MemberResDTO {

	public record SignUpResponse(
			Long memberId,
			String name,
			String phoneNum,
			String nickName
	) {
	}

	public record HomeMissionSummary(
			Long missionId,
			String title,
			String status
	) {
	}

	public record HomeResponse(
			Long userId,
			String nickName,
			List<HomeMissionSummary> missions
	) {
	}
}

