package com.umc10th.umc10th.domain.mission.entity.mapping;

import com.umc10th.umc10th.domain.mission.entity.Mission;
import com.umc10th.umc10th.domain.mission.enums.MissionStatus;
import com.umc10th.umc10th.domain.user.entity.User;
import com.umc10th.umc10th.global.common.BaseEntity;
import jakarta.persistence.*;
import jdk.jshell.Snippet;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
public class UserMission extends BaseEntity {

    @Id @Column(name = "user_mission_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private MissionStatus missionStatus = MissionStatus.CHALLENGING;


    @Builder.Default
    @Column(nullable = false, updatable = false)
    private LocalDateTime startMission = LocalDateTime.now();


    private LocalDateTime endMission;

}
