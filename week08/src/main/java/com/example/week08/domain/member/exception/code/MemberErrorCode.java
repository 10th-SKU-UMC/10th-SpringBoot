package com.example.week08.domain.member.exception.code;

import com.example.week08.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    // Member를 DB에서 찾지 못했을 때 사용하는 에러 코드
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404_1", "해당 유저를 찾을 수 없습니다."),

    // 이미 가입된 이메일로 회원가입 시도 시 사용하는 에러 코드
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "MEMBER409_1", "이미 사용 중인 이메일입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
