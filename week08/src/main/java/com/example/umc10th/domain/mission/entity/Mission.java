package com.example.umc10th.domain.mission.entity;

import com.example.umc10th.domain.store.entity.Store;
import com.example.umc10th.global.apiPayload.code.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mission")
public class Mission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "mission_id")
    private Long id;

    @Column(name = "point")
    private Integer point;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Column(name = "conditional")
    private String conditional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;
}