package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r JOIN FETCH r.member WHERE r.store.id = :storeId ORDER BY r.createdAt DESC")
    Page<Review> findByStoreId(@Param("storeId") Long storeId, Pageable pageable);

    @Query("SELECT r FROM Review r JOIN FETCH r.store WHERE r.member.id = :memberId ORDER BY r.createdAt DESC")
    Page<Review> findByMemberId(@Param("memberId") Long memberId, Pageable pageable);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.store.id = :storeId")
    Double findAvgRatingByStoreId(@Param("storeId") Long storeId);

    // 커서 기반 — ID 내림차순 (첫 페이지: cursor 없음)
    @Query("SELECT r FROM Review r JOIN FETCH r.store WHERE r.member.id = :memberId ORDER BY r.id DESC")
    List<Review> findByMemberIdOrderByIdDesc(@Param("memberId") Long memberId, Pageable pageable);

    // 커서 기반 — ID 내림차순 (cursor 이후)
    @Query("SELECT r FROM Review r JOIN FETCH r.store WHERE r.member.id = :memberId AND r.id < :cursor ORDER BY r.id DESC")
    List<Review> findByMemberIdCursorByIdDesc(@Param("memberId") Long memberId, @Param("cursor") Long cursor, Pageable pageable);

    // 커서 기반 — 별점 내림차순, 같은 별점은 ID 내림차순 (첫 페이지)
    @Query("SELECT r FROM Review r JOIN FETCH r.store WHERE r.member.id = :memberId ORDER BY r.rating DESC, r.id DESC")
    List<Review> findByMemberIdOrderByRatingDesc(@Param("memberId") Long memberId, Pageable pageable);

    // 커서 기반 — 별점 내림차순 (cursor 이후: 별점이 낮거나, 같은 별점이면 id가 작은 것)
    @Query("SELECT r FROM Review r JOIN FETCH r.store WHERE r.member.id = :memberId " +
            "AND (r.rating < :cursorRating OR (r.rating = :cursorRating AND r.id < :cursorId)) " +
            "ORDER BY r.rating DESC, r.id DESC")
    List<Review> findByMemberIdCursorByRatingDesc(@Param("memberId") Long memberId,
                                                   @Param("cursorRating") Integer cursorRating,
                                                   @Param("cursorId") Long cursorId,
                                                   Pageable pageable);
}
