package com.malgn.malgncms.auth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * packageName    : com.malgn.malgncms.jwt
 * fileName       : JwtProvider
 * author         : JAEIK
 * date           : 3/7/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/7/26        JAEIK       최초 생성
 */
public class JwtProvider {
    private final SecretKey secretKey;
    private final long maxAge;

    public JwtProvider(String key, long maxAge) {
        this.secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        this.maxAge = maxAge;
    }

    public String createJwt(String subject, Map<String, ?> claims) {

        Date issuedAt = new Date();
        Date expiration = new Date(System.currentTimeMillis() + maxAge);

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(secretKey)
                .compact();
    }
}
