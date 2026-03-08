package com.malgn.malgncms.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.malgn.malgncms.auth.dto
 * fileName       : loginRequest
 * author         : JAEIK
 * date           : 3/8/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/8/26        JAEIK       최초 생성
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    @Size(max = 30)
    @NotBlank
    @Schema(description = "로그인 아이디", example = "test123")
    String username;

    @Size(max = 255)
    @NotBlank
    @Schema(description = "비밀번호", example = "A123456789")
    String password;
}
