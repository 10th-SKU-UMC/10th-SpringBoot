package com.umc10th.umc10th.domain.review.service;

import com.umc10th.umc10th.domain.review.converter.ReviewConverter;
import com.umc10th.umc10th.domain.review.dto.ReviewReqDto;
import com.umc10th.umc10th.domain.review.dto.ReviewResDto;
import com.umc10th.umc10th.domain.review.entity.Review;
import com.umc10th.umc10th.domain.review.repository.ReviewRepository;
import com.umc10th.umc10th.global.apiPayload.code.GeneralErrorCode;
import com.umc10th.umc10th.global.apiPayload.exception.ProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewResDto.Pagenation<ReviewResDto.GetMyReviewDto> getMyReviews(
            ReviewReqDto.GetMyReviews request) {

        PageRequest pageable = PageRequest.of(0, request.size() + 1);
        List<Review> reviews;

        if (request.lastId() == null) {
            // 첫 페이지: 커서 조건 없이 조회
            reviews = "SCORE".equals(request.sortType())
                    ? reviewRepository.findByUserIdOrderByScore(request.userId(), pageable)
                    : reviewRepository.findByUserIdOrderById(request.userId(), pageable);
        } else {
            // 이후 페이지: 커서 조건 포함
            reviews = "SCORE".equals(request.sortType())
                    ? reviewRepository.findByUserIdOrderByScoreWithCursor(
                            request.userId(), request.lastScore(), request.lastId(), pageable)
                    : reviewRepository.findByUserIdOrderByIdWithCursor(
                            request.userId(), request.lastId(), pageable);
        }

        if (reviews.isEmpty()) {
            throw new ProjectException(GeneralErrorCode.NOT_FOUND);
        }

        return ReviewConverter.toPagenation(reviews, request.size(), request.sortType());
    }
}
