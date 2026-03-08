package com.malgn.malgncms.contetns.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * packageName    : com.malgn.malgncms.contetns.dto
 * fileName       : ContentsRequest
 * author         : JAEIK
 * date           : 3/9/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/9/26        JAEIK       최초 생성
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentsRequest {

    @NotBlank
    @Size(max = 100)
    private String title;

    @NotBlank
    private String description;
}
