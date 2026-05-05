package com.umc10th.umc10th.domain.store.dto;

import java.util.List;

public class StoreReqDto {

    // 리뷰 작성 (POST: /api/users/me/missions/{missionId}/reviews)
    public record ReviewCreateDto(
            Float score,
            String content, // body -> content로 변경
            List<String> reviewImages
    ) {}
}
