package com.malgn.malgncms.auth.jwt;

import com.malgn.malgncms.auth.Role;
import com.malgn.malgncms.auth.service.JwtAuthenticationProxyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * packageName    : com.malgn.malgncms.jwt
 * fileName       : JwtParserTest
 * author         : JAEIK
 * date           : 3/8/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/8/26        JAEIK       최초 생성
 */
class JwtParserTest {
    private JwtParser jwtParser;
    private JwtAuthenticationProxyService jwtAuthenticationProxyService;
    private Long maxAge = 3600000L;
    @BeforeEach
    void setUp() {
        // 1. 공통 문자열 키
        String secretString = "c3ByaW5nLWJvb3Qtand0LXR1dG9yaWFsLXNlY3JldC1rZXktZ2VuZXJhdGVkLWJ5LW1hbGdhbg==";

        jwtParser = new JwtParser(secretString);

        jwtAuthenticationProxyService = new JwtAuthenticationProxyService(secretString, maxAge);
    }

    @Test
    @DisplayName("토큰을 파싱하면 저장된 정보와 타입이 일치 테스트")
    void jwtParserTest() {
        // given
        Long id = 1L;
        String username = "김재익";
        Role role = Role.USER;
        JwtToken result = jwtAuthenticationProxyService.createToken(id, username, role);

        String fullToken = result.getAccessToken();

        // "Bearer "를 제거한 순수 토큰만 추출
        String pureToken = fullToken.substring(7);

        // when
        Map<String, Object> claims = jwtParser.parseClaims(pureToken);
        // then
        assertThat(String.valueOf(claims.get("id"))).isEqualTo(String.valueOf(id));
        assertThat(claims.get("sub")).isEqualTo(username);
        assertThat(claims.get("role")).isEqualTo(role.toString());
    }
}