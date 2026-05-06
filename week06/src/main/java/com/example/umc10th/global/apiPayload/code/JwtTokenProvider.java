package com.example.umc10th.global.apiPayload.code;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JwtTokenProvider { // 임시로 쓰는 토큰 클래스
    // 서버만 알고 있어야 하는 비밀키 (환경변수나 설정파일로 빼는게 좋음)
    private String secretKey = "very-secret-key-don-gyun-project-umc-10th";
    private long validityInMilliseconds = 3600000; // 1시간

    // 토큰 생성 메서드
    public String createToken(String userEmail) {
        Claims claims = Jwts.claims().setSubject(userEmail); // Payload에 담을 정보
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화
                .compact();
    }
}
