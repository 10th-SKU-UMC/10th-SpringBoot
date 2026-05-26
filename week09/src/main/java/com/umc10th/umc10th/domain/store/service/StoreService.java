package com.umc10th.umc10th.domain.store.service;

import com.umc10th.umc10th.domain.mission.entity.Mission;
import com.umc10th.umc10th.domain.mission.exception.MissionException;
import com.umc10th.umc10th.domain.mission.exception.code.MissionErrorCode;
import com.umc10th.umc10th.domain.mission.repository.MissionRepository;
import com.umc10th.umc10th.domain.review.entity.Review;
import com.umc10th.umc10th.domain.review.repository.ReviewRepository;
import com.umc10th.umc10th.domain.store.converter.StoreConverter;
import com.umc10th.umc10th.domain.store.dto.StoreReqDto;
import com.umc10th.umc10th.domain.store.dto.StoreResDto;
import com.umc10th.umc10th.domain.store.entity.Store;
import com.umc10th.umc10th.domain.store.exception.code.StoreErrorCode;
import com.umc10th.umc10th.domain.store.repository.StoreRepository;
import com.umc10th.umc10th.domain.user.entity.User;
import com.umc10th.umc10th.domain.user.exception.UserException;
import com.umc10th.umc10th.domain.user.exception.code.UserErrorCode;
import com.umc10th.umc10th.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final MissionRepository missionRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public StoreResDto.ReviewDetailDto createReview(StoreReqDto.ReviewCreateCommandDto command) {

        Store store = storeRepository.findById(command.storeId())
                .orElseThrow(() -> new IllegalStateException("가게를 찾을 수없습니다."));

        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        Mission mission = missionRepository.findById(command.missionId())
                .orElseThrow(() -> new MissionException(MissionErrorCode.MISSION_NOT_FOUND));

        Review review = StoreConverter.toReview(command);

        reviewRepository.save(review);

        return StoreConverter.toReviewDetailDto(review);
    }
}
