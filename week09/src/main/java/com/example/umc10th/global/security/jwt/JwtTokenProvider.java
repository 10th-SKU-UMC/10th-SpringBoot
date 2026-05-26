package com.example.umc10th.global.security.jwt;

import com.example.umc10th.global.security.CustomUserDetailsService;
import com.example.umc10th.member.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final long accessTokenExpirationMs;
    private final CustomUserDetailsService userDetailsService;

    public JwtTokenProvider(JwtProperties properties, CustomUserDetailsService userDetailsService) {
        this.key = Keys.hmacShaKeyFor(properties.getSecret().getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpirationMs = properties.getAccessTokenExpirationMs();
        this.userDetailsService = userDetailsService;
    }

    public String createAccessToken(Member member) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + accessTokenExpirationMs);

        return Jwts.builder()
                .setSubject(member.getEmail())
                .claim("memberId", member.getId())
                .claim("role", member.getRole())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        String email = getSubject(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private String getSubject(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}

