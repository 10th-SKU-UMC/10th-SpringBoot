package com.example.umc10th.global.apiPayload.code;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

//import java.security.Security;
import javax.crypto.SecretKey;
import java.util.Date;
@Component
public class JwtTokenProvider { // 임시로 쓰는 토큰 클래스
    // 서버만 알고 있어야 하는 비밀키 (환경변수나 설정파일로 빼는게 좋음)
    private final String secretKey = "very-secret-key-don-gyun-project-umc-10th";
    private final String secretKeyString = "very-secret-key-don-gyun-project-umc-10th-secured-32bytes";
    private final long validityInMilliseconds = 3600000; // 1시간

    // 문자열로 된 비밀키를 JJWT가 인식할 수 있는 SecretKey 객체로 변환하는 메서드
    private SecretKey getSigningKey(){
        // 만약 키가 Base64로 인코딩되어 있다면 Decoders.BASE64.decode()를 사용하고,
        // 일반 문자열이라면getBytes()를 사용
        byte[] keyBytes = secretKeyString.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // 토큰 생성 메서드
    public String createToken(String userEmail) {
        //Claims claims = Jwts.claims().setSubject(userEmail); // Payload에 담을 정보
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                //.setClaims(claims)
                .subject(userEmail)
                .setIssuedAt(now)
                .setExpiration(validity)
                //.signWith(SignatureAlgorithm.HS256, secretKey) // 암호화
                .signWith(getSigningKey())
                .compact();
    }
}
