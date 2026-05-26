package com.umc10th.umc10th.domain.store.dto;

import lombok.Builder;
import java.util.List;

public class StoreReqDto {

    // 클라이언트로부터 받는 순수 데이터 (RequestBody)
    public record ReviewCreateDto(
            Float score,
            String content,
            List<String> reviewImages
    ) {}

    // 서비스 계층으로 넘기기 위해 경로 변수와 데이터를 합친 객체
    @Builder
    public record ReviewCreateCommandDto(
            Long userId,
            Long storeId,
            Long missionId,
            ReviewCreateDto detail
    ) {
        // 컨트롤러에서 조립을 편하게 하기 위한 메서드
        public static ReviewCreateCommandDto of(Long userId, Long storeId, Long missionId, ReviewCreateDto request) {
            return ReviewCreateCommandDto.builder()
                    .userId(userId)
                    .storeId(storeId)
                    .missionId(missionId)
                    .detail(request)
                    .build();
        }
    }
}