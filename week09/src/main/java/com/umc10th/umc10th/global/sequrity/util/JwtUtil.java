package com.umc10th.umc10th.global.sequrity.util;

import com.umc10th.umc10th.domain.user.enums.Provider;
import com.umc10th.umc10th.global.sequrity.entity.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final Duration accessExpiration;

    public JwtUtil(
            @Value("${jwt.token.secretKey}") String secret,
            @Value("${jwt.token.expiration.access}") Long accessExpiration
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessExpiration = Duration.ofMillis(accessExpiration);
    }

    // AccessToken 생성
    public String createAccessToken(AuthUser user) {
        return createToken(user, accessExpiration);
    }

    /** 토큰에서 소셜 UID 가져오기 */
    public String getUid(String token) {
        try {
            return getClaims(token).getPayload().getSubject();
        } catch (JwtException e) {
            return null;
        }
    }

    /**
     * 토큰에서 소셜 로그인 타입 가져오기
     *
     * @param token 유저 정보를 추출할 토큰
     * @return 유저 소셜 로그인 타입을 추출합니다
     */
    public Provider getSocialType(String token) {
        try {
            Object socialType = getClaims(token).getPayload().get("social_type");
            if (socialType == null) return null;
            return Provider.valueOf(socialType.toString().toUpperCase());
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    /** 토큰 유효성 확인
     *
     * @param token 유효한지 확인할 토큰
     * @return True, False 반환합니다
     */
    public boolean isValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // 토큰 생성
    private String createToken(AuthUser member, Duration expiration) {
        Instant now = Instant.now();

        // 인가 정보
        String authorities = member.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // OAuth 유저: socialUid를 subject로, 일반 유저: email을 subject로
        String subject = (member.getUser().getSocialProvider() != null)
                ? member.getUser().getSocialUid()
                : member.getUser().getEmail();

        return Jwts.builder()
                .subject(subject)
                .claim("role", authorities)
                .claim("social_type", member.getUser().getSocialProvider())
                .issuedAt(Date.from(now)) // 언제 발급한지
                .expiration(Date.from(now.plus(expiration))) // 언제까지 유효한지
                .signWith(secretKey) // sign할 Key
                .compact();
    }

    // 토큰 정보 가져오기
    private Jws<Claims> getClaims(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(secretKey)
                .clockSkewSeconds(60)
                .build()
                .parseSignedClaims(token);
    }
}