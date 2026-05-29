package com.example.week09.domain.member.exception.code;

import com.example.week09.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    // Member를 DB에서 찾지 못했을 때 사용하는 에러 코드
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404_1", "해당 유저를 찾을 수 없습니다."),

    // 이미 가입된 이메일로 회원가입 시도 시 사용하는 에러 코드
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "MEMBER409_1", "이미 사용 중인 이메일입니다."),

    // 비밀번호가 일치하지 않을 때 사용하는 에러 코드
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "MEMBER401_1", "비밀번호가 올바르지 않습니다."),

    // 지원하지 않는 소셜 로그인 제공자로 시도할 때 사용하는 에러 코드
    NOT_SUPPORT_SOCIAL_PROVIDER(HttpStatus.BAD_REQUEST, "MEMBER400_1", "지원하지 않는 소셜 로그인 제공자입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
