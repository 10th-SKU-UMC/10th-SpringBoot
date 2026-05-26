package com.umc10th.umc10th.domain.store.converter;

import com.umc10th.umc10th.domain.review.entity.Review;
import com.umc10th.umc10th.domain.store.dto.StoreReqDto;
import com.umc10th.umc10th.domain.store.dto.StoreResDto;

public class StoreConverter {
    public static Review toReview(StoreReqDto.ReviewCreateCommandDto command) {
        return Review.builder()
                .content(command.detail().content())
                .score(command.detail().score())
                .build();

    }
    public static StoreResDto.ReviewDetailDto toReviewDetailDto(Review review) {

        return StoreResDto.ReviewDetailDto.builder()
                .reviewId(review.getId())
                .ownerNickname(review.getUser().getName())
                .score(review.getScore())
                .body(review.getContent())
                .build();


    }
}
