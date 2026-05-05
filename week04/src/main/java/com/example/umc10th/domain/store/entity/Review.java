package com.example.umc10th.domain.store.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "review_id")
    private Long id;

    private Float star;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "Field", length = 255)
    private String field;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "user_id")
    private Long userId;
}