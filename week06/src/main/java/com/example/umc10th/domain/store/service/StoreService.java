package com.example.umc10th.domain.store.service;

import com.example.umc10th.domain.store.converter.StoreConverter;
import com.example.umc10th.domain.store.dto.StoreReqDTO;
import com.example.umc10th.domain.store.entity.Review;
import com.example.umc10th.domain.store.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final ReviewRepository reviewRepository;

    // 리뷰 작성 로직
    @Transactional
    public Review createReview(Long storeId, StoreReqDTO.CreateReviewReqDTO request){
        Review newReview = StoreConverter.toCreateReview(request, storeId);
        return reviewRepository.save(newReview);
    }
}
