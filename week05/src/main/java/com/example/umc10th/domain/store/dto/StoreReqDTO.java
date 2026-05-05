package com.example.umc10th.domain.store.dto;

public class StoreReqDTO {

    // 리류 작성 요청
    public record CreateReviewReqDTO(
            Long userId,
            String content,
            Float star
    ){}
}
