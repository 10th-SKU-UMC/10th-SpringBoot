package com.example.week09.global.security.handler;

import com.example.week09.domain.member.converter.MemberConverter;
import com.example.week09.domain.member.dto.MemberResDTO;
import com.example.week09.domain.member.exception.code.MemberSuccessCode;
import com.example.week09.global.apiPayload.ApiResponse;
import com.example.week09.global.apiPayload.code.BaseSuccessCode;
import com.example.week09.global.security.entity.AuthMember;
import com.example.week09.global.security.entity.OAuthMember;
import com.example.week09.global.security.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

// OAuth2 로그인 성공 후 JWT 토큰을 발급하여 응답으로 내려주는 핸들러
@RequiredArgsConstructor
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        BaseSuccessCode code = MemberSuccessCode.OK;

        // Content-Type, Status 설정
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(code.getStatus().value());

        // SecurityContextHolder에서 OAuth 인증 객체 추출
        OAuthMember member = (OAuthMember) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        // OAuth 인증 객체의 Member로 JWT 액세스 토큰 발급
        String accessToken = jwtUtil.createAccessToken(new AuthMember(member.getMember()));

        // 응답 통일 객체로 래핑
        ApiResponse<MemberResDTO.Login> responseBody = ApiResponse.onSuccess(
                code,
                MemberConverter.toLogin(accessToken)
        );

        // 응답 출력
        objectMapper.writeValue(response.getOutputStream(), responseBody);
    }
}
