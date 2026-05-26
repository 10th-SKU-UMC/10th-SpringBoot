package com.example.week08.domain.mission.entity;

import com.example.week08.global.enums.Address;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long id;

    @Builder.Default
    @Enumerated(EnumType.STRING) // Enum을 문자열로 DB에 저장
    @Column(name = "name", nullable = false)
    private Address name = Address.NONE; // 지역명, 기본값 NONE
}
