package com.example.umc10th.global.apiPayload.code;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@MappedSuperclass
// 부모 클래스를 상속 받는 자식 클래스에게 부모 클래스가 가지는 칼럼만 매핑정보로 제공하고 싶다 선언하는 어노테이션
@EntityListeners(AuditingEntityListener.class)
//특정 이벤트(저장, 수정 등)가 발생했을 때, 이를 감지하여 자동으로 특정 작업을 수행
@Getter
public abstract class BaseEntity{

    @CreatedDate // 저장 이벤트가 발생했을때 해당 칼럼값을 현재 시각으로 저장
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // 수정 이벤트가 발생했을때 해당 칼럼값을 현재 시각으로 저장
    @Column(name = "update_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}