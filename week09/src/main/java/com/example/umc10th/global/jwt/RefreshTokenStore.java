package com.example.umc10th.global.jwt;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 이메일 → 리프레시 토큰을 인메모리로 관리.
 * 서버 재시작 시 초기화되므로 실제 운영에서는 Redis 등으로 교체 권장.
 */
@Component
public class RefreshTokenStore {

    private final ConcurrentHashMap<String, String> store = new ConcurrentHashMap<>();

    public void save(String email, String refreshToken) {
        store.put(email, refreshToken);
    }

    public boolean isValid(String email, String refreshToken) {
        return refreshToken.equals(store.get(email));
    }

    public void delete(String email) {
        store.remove(email);
    }
}
