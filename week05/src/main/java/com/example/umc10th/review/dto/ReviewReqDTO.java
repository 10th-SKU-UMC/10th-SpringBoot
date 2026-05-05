package com.example.umc10th.review.dto;

public class ReviewReqDTO {

	public record CreateReviewRequest(
			Long missionId,
			String content,
			Integer rating
	) {
	}
}

