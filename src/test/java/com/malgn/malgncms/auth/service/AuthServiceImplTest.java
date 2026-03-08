package com.malgn.malgncms.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.malgn.malgncms.auth.Role;
import com.malgn.malgncms.auth.dto.LoginRequest;
import com.malgn.malgncms.auth.dto.LoginResponse;
import com.malgn.malgncms.auth.jwt.JwtToken;
import com.malgn.malgncms.auth.repository.AuthRepository;
import com.malgn.malgncms.domain.entity.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

/**
 * packageName    : com.malgn.malgncms.auth.service
 * fileName       : AuthServiceImplTest
 * author         : JAEIK
 * date           : 3/8/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/8/26        JAEIK       최초 생성
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private AuthRepository authRepository;
    @InjectMocks
    private AuthServiceImpl authService;
    @Mock
    private JwtAuthenticationProxyService jwtAuthenticationProxyService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("로그인 성공 - 모든 조건이 맞으면 토큰과 유저명을 반환한다")
    void login_Success() {
        // 1. Given (준비)
        String username = "jaeik";
        String password = "password123";
        String encodedPassword = "encoded_password123";
        Long userId = 1L;
        Role role = Role.USER;
        JwtToken mockToken = new JwtToken("access-token-123");
        LoginRequest request = new LoginRequest(username, password);
        Users user = Users.builder()
                .id(userId)
                .username(username)
                .password(encodedPassword)
                .role(role)
                .build();

        // Jwt Mock 제대로된 jwt 생성 불가 임의이 토큰으로 생성
        when(authRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(jwtAuthenticationProxyService.createToken(userId, username, role)
                ).thenReturn(mockToken);

        // 2. When (실행)
        LoginResponse response = authService.login(request);

        // 3. Then (검증)
        assertThat(response.getUsername()).isEqualTo(username);
        assertThat(response.getToken()).isEqualTo(mockToken.getAccessToken());
        assertThat(response.getLoginTime()).isNotNull();

        // 토큰 생성 시 이상한 값을 넘기지는 않는지 테스트
        verify(jwtAuthenticationProxyService).createToken(eq(userId), eq(username), eq(role));
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호가 일치하지 않으면 예외가 발생한다")
    void login_Fail_PasswordMismatch() {
        String username = "jaeik";

        LoginRequest request = new LoginRequest(username, "1111111111111");

        String encodedPassword = "encoded_password123";

        Users user = Users.builder()
                .id(1L)
                .username(username)
                .password(encodedPassword)
                .role(Role.USER)

                .build();

        // 1. Given (비밀번호가 다르다고 설정)
        when(authRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("패스워드가 일치하지 않습니다.");

        // 비밀번호가 틀렸으므로 토큰 생성은 절대 호출되면 안 됨 !
        verify(jwtAuthenticationProxyService, never()).createToken(any(), any(), any());
    }
}