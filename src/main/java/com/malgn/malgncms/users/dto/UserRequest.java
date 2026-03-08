package com.malgn.malgncms.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.malgn.malgncms.users.dto
 * fileName       : UserReqeust
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
@Builder
public class UserRequest {
    @NotBlank
    @Size(max = 30)
    private String username;

    @NotBlank
    @Size(max = 255)
    private String password;
}
