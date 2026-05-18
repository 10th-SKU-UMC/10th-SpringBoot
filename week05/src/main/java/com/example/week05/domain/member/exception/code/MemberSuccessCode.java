package com.example.week05.domain.member.exception.code;

import com.example.week05.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberSuccessCode implements BaseSuccessCode {

    SIGN_UP_OK(HttpStatus.OK, "MEMBER200_1", "회원가입이 완료되었습니다."),
    GET_HOME_OK(HttpStatus.OK, "MEMBER200_2", "홈 화면 조회에 성공했습니다."),
    GET_MY_PAGE_OK(HttpStatus.OK, "MEMBER200_3", "마이페이지 조회에 성공했습니다."),
    UPDATE_NICKNAME_OK(HttpStatus.OK, "MEMBER200_4", "닉네임 변경에 성공했습니다."),
    UPDATE_PHONE_OK(HttpStatus.OK, "MEMBER200_5", "전화번호 변경에 성공했습니다."),
    UPDATE_NOTIFICATION_OK(HttpStatus.OK, "MEMBER200_6", "알림 설정 변경에 성공했습니다."),
    CREATE_INQUIRY_OK(HttpStatus.OK, "MEMBER200_7", "문의가 등록되었습니다."),
    LOGOUT_OK(HttpStatus.OK, "MEMBER200_8", "로그아웃이 완료되었습니다."),
    DELETE_OK(HttpStatus.OK, "MEMBER200_9", "회원 탈퇴가 완료되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
