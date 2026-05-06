package com.example.umc10th.domain.mission.entity;

import com.example.umc10th.global.apiPayload.code.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_mission")
public class MemberMission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_mission_id")
    private Long id;

    @Column(name = "is_complete")
    private Boolean isComplete;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "mission_id2") // 스샷에 mission_id2라고 되어 있어서 그대로 반영했습니다.
    private Long missionId;

    // 완료 상태로 바꾸는 메서드(서비스에서 호츌)
    public void complete(){
        this.isComplete = true;
    }
}