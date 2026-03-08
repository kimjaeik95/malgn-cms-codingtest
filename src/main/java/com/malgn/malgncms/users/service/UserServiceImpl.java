package com.malgn.malgncms.users.service;

import com.malgn.malgncms.auth.Role;
import com.malgn.malgncms.domain.entity.Users;
import com.malgn.malgncms.users.dto.UserRequest;
import com.malgn.malgncms.users.dto.UserResponse;
import com.malgn.malgncms.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.malgn.malgncms.users.service
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
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponse signUp(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new IllegalArgumentException("중복된 username 있습니다.");
        }
        String password = passwordEncoder.encode(userRequest.getPassword());
        Users newUser = Users.toEntity(userRequest, password, Role.USER);
        userRepository.save(newUser);
        return UserResponse.toDto(newUser);
    }

    /*
        서비스 정책에 따라 Admin 계정을 생성하는지 회원 승격하는지 다르지만
        H2 휘발성 메모리 과제테스트의 편의를 위해 Admin 생성로직을 작성합니다.
     */
    @Override
    @Transactional
    public UserResponse signUpAdmin(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new IllegalArgumentException("중복된 username 있습니다.");
        }
        String password = passwordEncoder.encode(userRequest.getPassword());
        Users newUser = Users.toEntity(userRequest, password, Role.ADMIN);
        userRepository.save(newUser);
        return UserResponse.toDto(newUser);
    }


}
