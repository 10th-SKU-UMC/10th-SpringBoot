package com.umc10th.umc10th.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class UserPoint {

    @Id @Column(name = "user_point_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "userPoint")
    private User user;

    @Column(name = "current_total")
    private int current_total;
}
