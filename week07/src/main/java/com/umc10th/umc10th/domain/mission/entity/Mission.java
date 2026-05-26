package com.umc10th.umc10th.domain.mission.entity;

import com.umc10th.umc10th.domain.mission.entity.mapping.UserMission;
import com.umc10th.umc10th.domain.store.entity.Store;
import com.umc10th.umc10th.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import tools.jackson.core.ObjectReadContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Mission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Long id;

    @Builder.Default
    @Column(name = "reward", nullable = false)
    private int reward = 0;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @Builder.Default // 빌더 사용 시에도 빈 리스트로 초기화 보장
    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL)
    private List<UserMission> userMissionList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;
}
