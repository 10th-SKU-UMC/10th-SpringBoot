package com.umc10th.umc10th.domain.review.repository;

import com.umc10th.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 첫 페이지 - ID 순
    @Query("SELECT r FROM Review r JOIN FETCH r.store LEFT JOIN FETCH r.reviewReplyList " +
            "WHERE r.user.id = :userId ORDER BY r.id DESC")
    List<Review> findByUserIdOrderById(
            @Param("userId") Long userId, Pageable pageable);

    // 첫 페이지 - 별점 순
    @Query("SELECT r FROM Review r JOIN FETCH r.store LEFT JOIN FETCH r.reviewReplyList " +
            "WHERE r.user.id = :userId ORDER BY r.score DESC, r.id DESC")
    List<Review> findByUserIdOrderByScore(
            @Param("userId") Long userId, Pageable pageable);

    // 커서 이후 - ID 순
    @Query("SELECT r FROM Review r JOIN FETCH r.store LEFT JOIN FETCH r.reviewReplyList " +
            "WHERE r.user.id = :userId AND r.id < :lastId ORDER BY r.id DESC")
    List<Review> findByUserIdOrderByIdWithCursor(
            @Param("userId") Long userId,
            @Param("lastId") Long lastId,
            Pageable pageable);

    // 커서 이후 - 별점 순 (동점 시 id 내림차순 2차 정렬)
    @Query("SELECT r FROM Review r JOIN FETCH r.store LEFT JOIN FETCH r.reviewReplyList " +
            "WHERE r.user.id = :userId " +
            "AND (r.score < :lastScore OR (r.score = :lastScore AND r.id < :lastId)) " +
            "ORDER BY r.score DESC, r.id DESC")
    List<Review> findByUserIdOrderByScoreWithCursor(
            @Param("userId") Long userId,
            @Param("lastScore") Float lastScore,
            @Param("lastId") Long lastId,
            Pageable pageable);
}
