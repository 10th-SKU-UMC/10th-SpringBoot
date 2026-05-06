package com.example.umc10th.domain.store.controller;


import com.example.umc10th.domain.store.dto.StoreReqDTO;
import com.example.umc10th.domain.store.dto.StoreResDTO;
import com.example.umc10th.domain.store.exception.code.StoreSuccessCode;
import com.example.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class StoreController {

    @PostMapping("/v1/stores/{store_id}/reviews")
    public ApiResponse<StoreResDTO.CreateReviewResDTO> createReview(
            @PathVariable(name = "store_id") Long store_id, // 경로 지정, 주소의 값을 꺼내서 사용
            @RequestBody StoreReqDTO.CreateReviewReqDTO request
    ){
        return ApiResponse.onSuccess(StoreSuccessCode.OK, null);
    }
}
