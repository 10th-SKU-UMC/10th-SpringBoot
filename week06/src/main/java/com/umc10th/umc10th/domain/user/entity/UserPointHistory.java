package com.umc10th.umc10th.domain.user.entity;

import com.umc10th.umc10th.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPointHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_point_history_id")
    private Long id;

    @Column(nullable = false)
    private int amount; // 변동 포인트 (증가는 양수, 차감은 음수)

    @Column(nullable = false)
    private String reason; // 변동 사유

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
