package com.example.umc10th.review.dto;

public class ReviewReqDTO {

	public record CreateReviewRequest(
			Long memberId,
			Long missionId,
			String content,
			Integer rating
	) {
	}
}
