package com.malgn.malgncms.users.service;

import com.malgn.malgncms.domain.entity.Users;
import com.malgn.malgncms.users.dto.UserRequest;
import com.malgn.malgncms.users.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;
import org.mockito.Mock;



/**
 * packageName    : com.malgn.malgncms.users.service
 * fileName       : UserServiceImplTest
 * author         : JAEIK
 * date           : 3/8/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/8/26        JAEIK       최초 생성
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService; // 테스트 대상 (가짜 객체들이 주입됨)

    @Mock
    private UserRepository userRepository; // 가짜 저장소

    @Mock
    private PasswordEncoder passwordEncoder; // 가짜 암호화 도구

    @Test
    @DisplayName("회원가입 성공, 패스워드 암호화 단위 테스트")
    void signUp_Success() {
        // 1. Given (준비)
        String rawPassword = "password123";
        String encodedPassword = "ENC_password123_ABC"; // 가짜 암호화 결과값
        UserRequest request = new UserRequest("jaeik", rawPassword);

        // 가짜 객체들의 행동 정의 (Stubbing)
        when(userRepository.existsByUsername("jaeik")).thenReturn(false);
        // encode("password123")이 호출되면 무조건 encodedPassword를 뱉어라!
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        // save 호출 시 들어온 객체를 그대로 반환 (Id 생성 등이 필요하면 여기서 처리)
        when(userRepository.save(any(Users.class))).thenAnswer(inv -> inv.getArgument(0));

        // 2. When (실행)
        userService.signUp(request);

        // 3. Then (검증)

        // 💡 검증 1: 암호화가 정확히 1번 수행되었는가?
        verify(passwordEncoder, times(1)).encode(rawPassword);

        // 💡 검증 2: Repository에 저장(save)된 Users 객체의 비밀번호가 암호화된 값인가?
        // argThat을 써서 넘겨진 Users 객체의 내부 상태를 직접 검사합니다.
        verify(userRepository).save(argThat(user -> {
            // 이 안에서 재익님이 궁금해하신 "값이 변경되었는지"를 체크합니다.
            boolean isUsernameMatch = user.getUsername().equals("jaeik");
            boolean isPasswordEncoded = user.getPassword().equals(encodedPassword);

            return isUsernameMatch && isPasswordEncoded;
        }));
    }
}
