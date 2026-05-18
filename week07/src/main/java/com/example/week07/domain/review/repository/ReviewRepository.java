package com.example.week07.domain.review.repository;

import com.example.week07.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 특정 가게의 리뷰 목록을 최신순으로 페이징 조회
    @Query("SELECT r FROM Review r WHERE r.store.id = :storeId ORDER BY r.createdAt DESC")
    Page<Review> findByStoreId(@Param("storeId") Long storeId, Pageable pageable);

    // 내 리뷰 목록 - ID 순 (커서 없음)
    Slice<Review> findByMember_IdOrderByIdDesc(Long memberId, Pageable pageable);

    // 내 리뷰 목록 - ID 순 (커서 있음: review_id < cursorId)
    Slice<Review> findByMember_IdAndIdLessThanOrderByIdDesc(Long memberId, Long cursorId, Pageable pageable);

    // 내 리뷰 목록 - 별점 순 (커서 없음)
    @Query("SELECT r FROM Review r WHERE r.member.id = :memberId ORDER BY r.star DESC, r.id DESC")
    Slice<Review> findByMemberIdOrderByStarDesc(@Param("memberId") Long memberId, Pageable pageable);

    // 내 리뷰 목록 - 별점 순 (커서 있음: star < cursorStar OR (star = cursorStar AND id < cursorId))
    @Query("SELECT r FROM Review r WHERE r.member.id = :memberId AND (r.star < :cursorStar OR (r.star = :cursorStar AND r.id < :cursorId)) ORDER BY r.star DESC, r.id DESC")
    Slice<Review> findByMemberIdOrderByStarDescWithCursor(
            @Param("memberId") Long memberId,
            @Param("cursorStar") BigDecimal cursorStar,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );
}
