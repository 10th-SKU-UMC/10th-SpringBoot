package com.example.umc10th.mission.entity.mapping;

import com.example.umc10th.member.entity.Member;
import com.example.umc10th.mission.entity.Mission;
import com.example.umc10th.mission.enums.MissionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class MemberMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_mission_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MissionStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    public Long getId() {
        return id;
    }

    public MissionStatus getStatus() {
        return status;
    }

    public void setStatus(MissionStatus status) {
        this.status = status;
    }

    public Member getMember() {
        return member;
    }

    public Mission getMission() {
        return mission;
    }
}
