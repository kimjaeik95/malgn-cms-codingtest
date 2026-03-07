package com.malgn.malgncms.users.service;

import com.malgn.malgncms.domain.dto.Role;
import com.malgn.malgncms.jwt.JwtToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * packageName    : com.malgn.malgncms.users.service
 * fileName       : JwtAuthenticationProxyServiceTest
 * author         : JAEIK
 * date           : 3/7/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/7/26        JAEIK       최초 생성
 */
class JwtAuthenticationProxyServiceTest {

    private JwtAuthenticationProxyService jwtAuthenticationProxyService;
    private final String secretKey = "c3ByaW5nLWJvb3Qtand0LXR1dG9yaWFsLXNlY3JldC1rZXktZ2VuZXJhdGVkLWJ5LW1hbGdhbg==";
    private final Long masAge = 3600000L;

    @BeforeEach
    void setUp() {
        jwtAuthenticationProxyService = new JwtAuthenticationProxyService(secretKey, masAge);
    }

    @Test
    @DisplayName("Jwt 액세스 토큰이 Bearer + secretKey 맞는지 테스트")
    void createToken() {
        // given
        Long id = 1L;
        String username = "김재익";
        Role role = Role.USER;

        // when
        JwtToken result = jwtAuthenticationProxyService.createToken(id, username, role);
        String accessToken = result.getAccessToken();
        // then
        assertThat(accessToken).isNotEmpty();
        assertThat(result.getAccessToken()).startsWith("Bearer ").isNotNull();
        System.out.println(accessToken);
    }
}