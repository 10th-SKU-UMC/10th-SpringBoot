package com.umc10th.umc10th.domain.review.repository;

import com.umc10th.umc10th.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
