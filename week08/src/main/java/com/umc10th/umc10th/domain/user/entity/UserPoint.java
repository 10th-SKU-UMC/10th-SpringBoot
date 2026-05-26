package com.umc10th.umc10th.domain.user.entity;

import com.umc10th.umc10th.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPoint extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_point_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "userPoint")
    private User user;

    @Builder.Default
    @Column(name = "current_total", nullable = false)
    private int currentTotal = 0;
}