package com.malgn.malgncms.auth.service;

import com.malgn.malgncms.auth.dto.LoginRequest;
import com.malgn.malgncms.auth.dto.LoginResponse;
import com.malgn.malgncms.auth.jwt.JwtToken;
import com.malgn.malgncms.auth.repository.AuthRepository;
import com.malgn.malgncms.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * packageName    : com.malgn.malgncms.auth.service
 * fileName       : AuthServiceImpl
 * author         : JAEIK
 * date           : 3/8/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/8/26        JAEIK       최초 생성
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationProxyService jwtAuthenticationProxyService;
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User users = authRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(loginRequest.getPassword(), users.getPassword())) {
            throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
        }

        if (users.getRole() == null) {
            throw new IllegalStateException("권한이 없는 사용자입니다. 관리자에게 문의하세요.");
        }

        JwtToken jwtToken = jwtAuthenticationProxyService.createToken(users.getId(), users.getUsername(), users.getRole());
        return new LoginResponse(users.getUsername(), jwtToken.getAccessToken(), ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toInstant());
    }
}
