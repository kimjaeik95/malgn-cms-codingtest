package com.malgn.malgncms.users.dto;

import com.malgn.malgncms.auth.Role;
import com.malgn.malgncms.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * packageName    : com.malgn.malgncms.users.dto
 * fileName       : UserResponse
 * author         : JAEIK
 * date           : 3/8/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/8/26        JAEIK       최초 생성
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private Role role;
    private Instant createdDate;

    public static UserResponse toDto(User users) {
        return UserResponse.builder()
                .id(users.getId())
                .username(users.getUsername())
                .role(users.getRole())
                .createdDate(users.getCreatedDate())
                .build();
    }
}
