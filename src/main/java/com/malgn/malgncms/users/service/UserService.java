package com.malgn.malgncms.users.service;

import com.malgn.malgncms.users.dto.UserRequest;
import com.malgn.malgncms.users.dto.UserResponse;

/**
 * packageName    : com.malgn.malgncms.users.service
 * fileName       : UserService
 * author         : JAEIK
 * date           : 3/8/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/8/26        JAEIK       최초 생성
 */
public interface UserService {
    UserResponse signUp(UserRequest userRequest);

    UserResponse signUpAdmin(UserRequest userRequest);
}
