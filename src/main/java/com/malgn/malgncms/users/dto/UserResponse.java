package com.malgn.malgncms.users.dto;

import com.malgn.malgncms.auth.Role;
import com.malgn.malgncms.domain.entity.Users;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    public static UserResponse toDto(Users users) {
        return UserResponse.builder()
                .id(users.getId())
                .username(users.getUsername())
                .role(users.getRole())
                .createdDate(users.getCreatedDate())
                .build();
    }
}
