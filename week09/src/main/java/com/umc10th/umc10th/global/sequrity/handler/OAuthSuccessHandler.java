package com.umc10th.umc10th.global.sequrity.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc10th.umc10th.domain.user.dto.UserResDto;
import com.umc10th.umc10th.domain.user.exception.code.UserSuccessCode;
import com.umc10th.umc10th.global.apiPayload.ApiResponse;
import com.umc10th.umc10th.global.apiPayload.code.BaseSuccessCode;
import com.umc10th.umc10th.global.sequrity.entity.AuthUser;
import com.umc10th.umc10th.global.sequrity.entity.OAuthUser;
import com.umc10th.umc10th.global.sequrity.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import java.io.IOException;

@RequiredArgsConstructor
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        // 사전 작업: Response 매핑할 ObjectMapper 선언
        ObjectMapper objectMapper = new ObjectMapper();
        BaseSuccessCode code = UserSuccessCode.OK;

        // Content-Type, Status 설정
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(code.getStatus().value());

        // 인증 객체 컨테이너에서 OAuth 인증 객체 가져오기
        OAuthUser oAuthUser = (OAuthUser) authentication.getPrincipal();

        // 토큰 제작을 위해 OAuth 인증 객체에서 User 추출 -> AuthUser 제작
        String accessToken = jwtUtil.createAccessToken(new AuthUser(oAuthUser.getUser()));

        // 응답 통일 객체 래핑
        ApiResponse<UserResDto.LoginResultDto> responseBody = ApiResponse.onSuccess(
                code,
                UserResDto.LoginResultDto.builder()
                        .accessToken(accessToken)
                        .userId(oAuthUser.getUser().getId())
                        .build()
        );

        // 응답 출력
        objectMapper.writeValue(response.getOutputStream(), responseBody);
    }
}
