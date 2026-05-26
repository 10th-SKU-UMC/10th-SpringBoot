package com.example.week08.domain.mission.entity.mapping;

import com.example.week08.domain.member.entity.Member;
import com.example.week08.domain.mission.entity.Mission;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "member_mission")
public class MemberMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_mission_id")
    private Long id;

    @Builder.Default
    @Column(name = "is_complete", nullable = false)
    private Boolean isComplete = false; // 미션 완료 여부, 기본값 false(미완료)

    // 어떤 회원이 수행 중인지 (member 테이블과 연결)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 어떤 미션인지 (mission 테이블과 연결)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;
}
