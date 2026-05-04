package com.example.umc10th.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode implements BaseCode {

    // 공통
    OK(HttpStatus.OK, "COMMON200", "성공입니다."),
    CREATED(HttpStatus.CREATED, "COMMON201", "요청 성공 및 리소스 생성되었습니다."),

    // Member
    MEMBER_FOUND(HttpStatus.OK, "MEMBER200", "회원 조회 성공입니다."),
    MEMBER_UPDATED(HttpStatus.OK, "MEMBER201", "회원 정보 수정 성공입니다."),

    // Mission
    MISSION_FOUND(HttpStatus.OK, "MISSION200", "미션 조회 성공입니다."),
    MISSION_STARTED(HttpStatus.CREATED, "MISSION201", "미션 시작 성공입니다."),
    MISSION_COMPLETED(HttpStatus.OK, "MISSION202", "미션 완료 성공입니다."),

    // Review
    REVIEW_FOUND(HttpStatus.OK, "REVIEW200", "리뷰 조회 성공입니다."),
    REVIEW_CREATED(HttpStatus.CREATED, "REVIEW201", "리뷰 작성 성공입니다."),
    REVIEW_UPDATED(HttpStatus.OK, "REVIEW202", "리뷰 수정 성공입니다."),
    REVIEW_DELETED(HttpStatus.OK, "REVIEW203", "리뷰 삭제 성공입니다."),

    // Store
    STORE_FOUND(HttpStatus.OK, "STORE200", "가게 조회 성공입니다."),

    // Notification
    NOTIFICATION_FOUND(HttpStatus.OK, "NOTIFICATION200", "알림 조회 성공입니다."),
    NOTIFICATION_READ(HttpStatus.OK, "NOTIFICATION201", "알림 읽음 처리 성공입니다."),

    // Inquiry
    INQUIRY_CREATED(HttpStatus.CREATED, "INQUIRY201", "문의 등록 성공입니다."),
    INQUIRY_FOUND(HttpStatus.OK, "INQUIRY200", "문의 조회 성공입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
