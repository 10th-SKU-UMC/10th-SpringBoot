package com.example.umc10th.member.dto;

import com.example.umc10th.member.enums.Gender;
import com.example.umc10th.mission.enums.Address;
import java.time.LocalDate;
import java.util.List;

public class MemberResDTO {

	public record SignUpResponse(
			Long memberId,
			String name,
			String email,
			String phoneNum,
			String nickName,
			Gender gender,
			LocalDate birthDate,
			Address address,
			String detailAddress,
			List<String> preferredFoods
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
