package com.umc10th.umc10th.domain.review.converter;

import com.umc10th.umc10th.domain.review.dto.ReviewResDto;
import com.umc10th.umc10th.domain.review.entity.Review;

import java.util.List;

public class ReviewConverter {

    public static ReviewResDto.GetMyReviewDto toGetMyReviewDto(Review review) {
        String ownerReply = review.getReviewReplyList().isEmpty()
                ? null
                : review.getReviewReplyList().get(0).getContent();

        return ReviewResDto.GetMyReviewDto.builder()
                .reviewId(review.getId())
                .storeName(review.getStore().getName())
                .score(review.getScore())
                .body(review.getContent())
                .createdAt(review.getCreatedAt().toLocalDate())
                .ownerReply(ownerReply)
                .build();
    }

    public static ReviewResDto.Pagenation<ReviewResDto.GetMyReviewDto> toPagenation(
            List<Review> reviews, int size, String sortType) {

        boolean hasNext = reviews.size() > size;
        List<Review> result = hasNext ? reviews.subList(0, size) : reviews;

        List<ReviewResDto.GetMyReviewDto> dtoList = result.stream()
                .map(r -> ReviewConverter.toGetMyReviewDto(r))
                .toList();

        Review last = result.isEmpty() ? null : result.get(result.size() - 1);

        return ReviewResDto.Pagenation.<ReviewResDto.GetMyReviewDto>builder()
                .data(dtoList)
                .nextCursor(last != null ? last.getId() : null)
                .nextScoreCursor("SCORE".equals(sortType) && last != null ? last.getScore() : null)
                .hasNext(hasNext)
                .build();
    }
}
