package com.example.umc10th.domain.review.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exceptions.MemberException;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.mission.exception.MissionException;
import com.example.umc10th.domain.mission.repository.StoreRepository;
import com.example.umc10th.domain.review.converter.ReviewConverter;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Reply;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.entity.ReviewPhoto;
import com.example.umc10th.domain.review.exception.ReviewException;
import com.example.umc10th.domain.review.repository.ReplyRepository;
import com.example.umc10th.domain.review.repository.ReviewRepository;
import com.example.umc10th.global.apiPayload.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    public ReviewResDTO.ReviewListDTO getReviews(Long storeId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Review> reviewPage = reviewRepository.findByStoreId(storeId, pageable);
        Double avgRating = reviewRepository.findAvgRatingByStoreId(storeId);
        return ReviewConverter.toReviewListDTO(reviewPage, avgRating);
    }

    @Transactional
    public ReviewResDTO.ReviewCreateResultDTO createReview(Long memberId, Long storeId,
                                                           ReviewReqDTO.CreateReviewDTO request) {
        Member member = findMemberById(memberId);
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new MissionException(ErrorCode.STORE_NOT_FOUND));

        Review review = Review.builder()
                .member(member)
                .store(store)
                .rating(request.getRating())
                .content(request.getContent())
                .build();

        if (request.getImages() != null && !request.getImages().isEmpty()) {
            List<ReviewPhoto> photos = IntStream.range(0, request.getImages().size())
                    .mapToObj(i -> ReviewPhoto.builder()
                            .review(review)
                            .imageUrl(request.getImages().get(i))
                            .orderNum(i)
                            .build())
                    .collect(Collectors.toList());
            review.getPhotos().addAll(photos);
        }

        Review saved = reviewRepository.save(review);
        return ReviewConverter.toReviewCreateResultDTO(saved);
    }

    @Transactional
    public ReviewResDTO.ReviewDTO updateReview(Long memberId, Long reviewId,
                                               ReviewReqDTO.UpdateReviewDTO request) {
        Review review = findReviewById(reviewId);
        if (!review.getMember().getId().equals(memberId)) {
            throw new ReviewException(ErrorCode.REVIEW_NOT_AUTHORIZED);
        }
        review.update(request.getRating(), request.getContent());
        return ReviewConverter.toReviewDTO(review);
    }

    @Transactional
    public void deleteReview(Long memberId, Long reviewId) {
        Review review = findReviewById(reviewId);
        if (!review.getMember().getId().equals(memberId)) {
            throw new ReviewException(ErrorCode.REVIEW_NOT_AUTHORIZED);
        }
        reviewRepository.delete(review);
    }

    @Transactional
    public ReviewResDTO.ReviewReplyCreateResultDTO createReply(Long memberId, Long reviewId,
                                                               ReviewReqDTO.CreateReplyDTO request) {
        Member member = findMemberById(memberId);
        Review review = findReviewById(reviewId);

        Reply reply = Reply.builder()
                .review(review)
                .member(member)
                .content(request.getContent())
                .build();
        Reply saved = replyRepository.save(reply);
        return ReviewConverter.toReplyCreateResultDTO(saved);
    }

    @Transactional
    public void deleteReply(Long memberId, Long reviewId, Long replyId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new ReviewException(ErrorCode.REVIEW_NOT_FOUND));
        if (!reply.getMember().getId().equals(memberId)) {
            throw new ReviewException(ErrorCode.REVIEW_NOT_AUTHORIZED);
        }
        replyRepository.delete(reply);
    }

    public ReviewResDTO.ReviewListDTO getMyReviews(Long memberId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Review> reviewPage = reviewRepository.findByMemberId(memberId, pageable);
        return ReviewConverter.toReviewListDTO(reviewPage, null);
    }

    private Review findReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException(ErrorCode.REVIEW_NOT_FOUND));
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
