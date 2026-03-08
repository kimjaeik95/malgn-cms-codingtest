package com.malgn.malgncms.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.malgn.malgncms.auth
 * fileName       : AuthenticateMember
 * author         : JAEIK
 * date           : 3/8/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/8/26        JAEIK       최초 생성
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AuthenticateMember {
    private Long id;
    private String username;
    private String role;
}
