package com.example.umc10th.domain.store.converter;

import com.example.umc10th.domain.store.dto.StoreReqDTO;
import com.example.umc10th.domain.store.entity.Review;

import java.time.LocalDateTime;

public class StoreConverter {

    // 리뷰 작성
    public static Review toCreateReview(StoreReqDTO.CreateReviewReqDTO request, Long storeId){
        return Review.builder()
                .star(request.star())
                .content(request.content())
                .userId(request.userId())
                .build();
    }
}
