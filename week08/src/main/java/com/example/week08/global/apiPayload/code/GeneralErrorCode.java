package com.example.week08.global.apiPayload.code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GeneralErrorCode implements BaseErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400_1", "잘못된 요청입니다."),           // 400: 클라이언트가 잘못된 요청을 보낸 경우
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401_1", "인증되지 않았습니다."),       // 401: 로그인 안 한 상태에서 인증이 필요한 요청을 한 경우
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403_1", "접근이 금지되었습니다."),           // 403: 로그인은 했지만 권한이 없는 경우
    NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON404_1", "해당 리소스를 찾을 수 없습니다."), // 404: 요청한 URL이 존재하지 않는 경우
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500_1", "서버 내부 오류입니다."); // 500: 서버가 예상치 못한 오류로 터진 경우

    private final HttpStatus status;
    private final String code;
    private final String message;
}