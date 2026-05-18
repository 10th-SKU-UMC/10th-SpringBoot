package com.example.week07.domain.mission.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "store")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name; // 가게명

    @Column(name = "manager_number", nullable = false)
    private Long managerNumber; // 사장님 구분 번호

    @Column(name = "detail_address", nullable = false, length = 255)
    private String detailAddress; // 상세 주소

    // 가게가 속한 지역 (location 테이블과 연결)
    // LAZY: 실제로 location이 필요할 때만 DB 조회 (성능 최적화)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    // 이 가게에 등록된 미션 목록
    @Builder.Default
    @OneToMany(mappedBy = "store")
    private List<Mission> missions = new ArrayList<>();
}
