package com.malgn.malgncms.auth.jwt;

import com.malgn.malgncms.auth.Role;
import com.malgn.malgncms.auth.service.JwtAuthenticationProxyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * packageName    : com.malgn.malgncms.jwt
 * fileName       : JwtValidatorTest
 * author         : JAEIK
 * date           : 3/8/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/8/26        JAEIK       최초 생성
 */
class JwtValidatorTest {

    private JwtValidator jwtValidator;
    private JwtAuthenticationProxyService jwtAuthenticationProxyService;
    private Long maxAge = 3600000L;
    SecretKey secretKey;
    @BeforeEach
    void setUp() {
        // 1. 공통 문자열 키
        String secretString = "c3ByaW5nLWJvb3Qtand0LXR1dG9yaWFsLXNlY3JldC1rZXktZ2VuZXJhdGVkLWJ5LW1hbGdhbg==";

        jwtValidator = new JwtValidator(secretString);

        jwtAuthenticationProxyService = new JwtAuthenticationProxyService(secretString, maxAge);
    }
    @Test
    @DisplayName("Jwt 생성하고 검증하는 테스트")
    void validateTokenTest() {
        // given
        JwtToken result = jwtAuthenticationProxyService.createToken(1L, "김재익", Role.USER);
        String token = result.getAccessToken();

        String pureToken = token.substring(7);

        // when
        boolean test = jwtValidator.validateToken(pureToken);

        // then
        assertThat(test).isTrue();


    }
}