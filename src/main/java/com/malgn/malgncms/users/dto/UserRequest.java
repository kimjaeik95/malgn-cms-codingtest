package com.malgn.malgncms.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "로그인 아이디", example = "test123")
    private String username;

    @NotBlank
    @Size(max = 255)
    @Schema(description = "비밀번호", example = "A123456789")
    private String password;
}
