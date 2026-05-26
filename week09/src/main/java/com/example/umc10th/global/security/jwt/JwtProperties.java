package com.example.umc10th.global.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secret;
    private long accessTokenExpirationMs;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getAccessTokenExpirationMs() {
        return accessTokenExpirationMs;
    }

    public void setAccessTokenExpirationMs(long accessTokenExpirationMs) {
        this.accessTokenExpirationMs = accessTokenExpirationMs;
    }
}

