package com.example.umc10th.domain.store.entity;

import com.example.umc10th.global.apiPayload.code.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "store")
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "store_id")
    private Long id;

    @Column(length = 20)
    private String name;

    @Column(length = 50)
    private String address;

    @Column(name = "location_id")
    private Long locationId;

}