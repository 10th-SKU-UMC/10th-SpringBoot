package com.example.umc10th.domain.store.repository;

import com.example.umc10th.domain.store.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
