package com.example.week05.domain.review.service;

import com.example.week05.domain.review.dto.ReviewReqDTO;
import com.example.week05.domain.review.dto.ReviewResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    // 리뷰 작성 - 다음 주차에 구현 예정
    public ReviewResDTO.CreateReviewRes createReview(Long memberId, Long missionId, ReviewReqDTO.CreateReview dto) {
        return null;
    }

    // 내가 작성한 리뷰 목록 조회 - 다음 주차에 구현 예정
    public ReviewResDTO.ReviewListRes getMyReviews(Long memberId) {
        return null;
    }
}
