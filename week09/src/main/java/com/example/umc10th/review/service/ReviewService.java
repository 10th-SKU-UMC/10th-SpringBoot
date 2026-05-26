package com.example.umc10th.review.service;

import com.example.umc10th.member.entity.Member;
import com.example.umc10th.member.exception.MemberException;
import com.example.umc10th.member.exception.code.MemberErrorCode;
import com.example.umc10th.member.repository.MemberRepository;
import com.example.umc10th.mission.entity.Mission;
import com.example.umc10th.mission.entity.Store;
import com.example.umc10th.mission.repository.MissionRepository;
import com.example.umc10th.mission.repository.StoreRepository;
import com.example.umc10th.review.converter.ReviewConverter;
import com.example.umc10th.review.dto.ReviewReqDTO;
import com.example.umc10th.review.dto.ReviewResDTO;
import com.example.umc10th.review.entity.Review;
import com.example.umc10th.review.exception.ReviewException;
import com.example.umc10th.review.exception.code.ReviewErrorCode;
import com.example.umc10th.review.repository.ReviewRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final MissionRepository missionRepository;
    private final StoreRepository storeRepository;

    public ReviewService(
            ReviewRepository reviewRepository,
            MemberRepository memberRepository,
            MissionRepository missionRepository,
            StoreRepository storeRepository
    ) {
        this.reviewRepository = reviewRepository;
        this.memberRepository = memberRepository;
        this.missionRepository = missionRepository;
        this.storeRepository = storeRepository;
    }

    @Transactional
    public ReviewResDTO.CreateReviewResponse createReview(
            Long storeId,
            ReviewReqDTO.CreateReviewRequest request
    ) {
        Mission mission = missionRepository.findById(request.missionId())
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));

        if (!mission.getStore().getId().equals(storeId)) {
            throw new ReviewException(ReviewErrorCode.REVIEW_BAD_REQUEST);
        }

        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));

        Review review = new Review(
                member,
                store,
                BigDecimal.valueOf(request.rating()),
                request.content(),
                LocalDateTime.now()
        );

        Review saved = reviewRepository.save(review);
        return ReviewConverter.toCreateReviewResponse(saved, store.getId(), mission.getId());
    }

    @Transactional(readOnly = true)
    public ReviewResDTO.ReviewPageResponse getReviewsByStore(Long storeId, int page, int size) {
        if (page < 0 || size <= 0) {
            throw new ReviewException(ReviewErrorCode.REVIEW_BAD_REQUEST);
        }

        if (!storeRepository.existsById(storeId)) {
            throw new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND);
        }

        PageRequest pageable = PageRequest.of(page, size);
        Page<Review> reviewPage = reviewRepository.findByStoreId(storeId, pageable);
        return ReviewConverter.toReviewPageResponse(reviewPage);
    }

    @Transactional(readOnly = true)
    public ReviewResDTO.MyReviewCursorResponse getMyReviewsById(Long memberId, Long idCursor, int size) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }

        PageRequest pageable = PageRequest.ofSize(size);
        Slice<Review> slice = (idCursor == null)
                ? reviewRepository.findByMemberIdOrderByIdDesc(memberId, pageable)
                : reviewRepository.findByMemberIdAndIdLessThanOrderByIdDesc(memberId, idCursor, pageable);

        return ReviewConverter.toMyReviewCursorResponse(slice, "ID");
    }

    @Transactional(readOnly = true)
    public ReviewResDTO.MyReviewCursorResponse getMyReviewsByRating(
            Long memberId, BigDecimal starCursor, Long idCursor, int size
    ) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }

        PageRequest pageable = PageRequest.ofSize(size);
        Slice<Review> slice = (starCursor == null || idCursor == null)
                ? reviewRepository.findByMemberIdOrderByStarDescIdDesc(memberId, pageable)
                : reviewRepository.findByMemberIdWithStarCursorOrderByStarDescIdDesc(memberId, starCursor, idCursor, pageable);

        return ReviewConverter.toMyReviewCursorResponse(slice, "RATING");
    }
}
