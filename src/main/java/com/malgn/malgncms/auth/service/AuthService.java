package com.malgn.malgncms.auth.service;

import com.malgn.malgncms.auth.dto.LoginRequest;
import com.malgn.malgncms.auth.dto.LoginResponse;
import com.malgn.malgncms.auth.jwt.JwtToken;

/**
 * packageName    : com.malgn.malgncms.auth.service
 * fileName       : AuthService
 * author         : JAEIK
 * date           : 3/8/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/8/26        JAEIK       최초 생성
 */
public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
}
