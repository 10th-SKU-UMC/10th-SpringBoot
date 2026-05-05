package com.example.umc10th.member.dto;

public class MemberReqDTO {

	public record SignUpRequest(
			String name,
			String phoneNum,
			String nickName
	) {
	}
}

