package com.example.week07.domain.mission.entity;

import com.example.week07.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.week07.domain.mission.entity.mapping.MemberMission;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "mission")
public class Mission extends BaseEntity { // BaseEntity: createdAt, updatedAt, deletedAt 자동 관리

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Long id;

    @Column(name = "deadline", nullable = false)
    private LocalDate deadline; // 미션 기한

    @Column(name = "conditional", nullable = false, columnDefinition = "TEXT")
    private String conditional; // 미션 조건

    @Column(name = "point", nullable = false)
    private Integer point; // 성공 시 받을 포인트

    // 이 미션이 속한 가게 (store 테이블과 연결)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    // 이 미션을 수행 중인 사용자 목록 (member_mission 테이블과 연결)
    @Builder.Default
    @OneToMany(mappedBy = "mission")
    private List<MemberMission> memberMissions = new ArrayList<>();
}
