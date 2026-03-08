package com.malgn.malgncms.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

/**
 * packageName    : com.malgn.malgncms.auth.dto
 * fileName       : LoginResponse
 * author         : JAEIK
 * date           : 3/8/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/8/26        JAEIK       최초 생성
 */
@AllArgsConstructor
@Getter
public class LoginResponse {
    private String username;
    private String token;
    private Instant loginTime;
}
