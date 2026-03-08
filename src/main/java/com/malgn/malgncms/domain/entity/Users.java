package com.malgn.malgncms.domain.entity;

import com.malgn.malgncms.auth.Role;
import com.malgn.malgncms.users.dto.UserRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * packageName    : com.malgn.malgncms.domain.entity
 * fileName       : Users
 * author         : JAEIK
 * date           : 3/6/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/6/26        JAEIK       최초 생성
 */
@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "created_date")
    private Instant createdDate;

    public static Users toEntity(UserRequest userRequest, String encoderPassword, Role role) {
        return Users.builder()
                .username(userRequest.getUsername())
                .password(encoderPassword)
                .role(role)
                .createdDate(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toInstant())
                .build();
    }
}
