package com.malgn.malgncms.users.service;

import com.malgn.malgncms.domain.dto.Role;
import com.malgn.malgncms.jwt.JwtProperties;
import com.malgn.malgncms.jwt.JwtProvider;
import com.malgn.malgncms.jwt.JwtToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : com.malgn.malgncms.users.service
 * fileName       : JwtAuthenticationProxyService
 * author         : JAEIK
 * date           : 3/7/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/7/26        JAEIK       최초 생성
 */
@Service
public class JwtAuthenticationProxyService {
    /**
     * JWT 토큰 생성 서비스 : 로그인/회원 인증에서 사용할 JWT claims 캡슐화
     */
    private final JwtProvider jwtProvider;

    public JwtAuthenticationProxyService(
            @Value("${jwt.secret-key}") String keyParam,
            @Value("${jwt.max-age}") Long maxAge
    ) {
        if (maxAge == null) {
            maxAge = 3_600_000L;
        }

        this.jwtProvider = new JwtProvider(keyParam, maxAge);
    }

    public JwtToken createToken(Long id, String username, Role role) {
        String accessToken = buildJwt(id, username, role);
        return new JwtToken(accessToken);
    }

    private String buildJwt(Long id, String username, Role role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("username",username);
        claims.put("role", role);
        String token = jwtProvider.createJwt(username, claims);
        return JwtProperties.ACCESS_TOKEN_PREFIX + token;
    }
}
