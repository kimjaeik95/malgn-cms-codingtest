package com.malgn.malgncms.contetns.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "콘텐츠 제목", example = "임시 제목")
    private String title;

    @NotBlank
    @Schema(description = "콘텐츠 내용", example = "합격하게 해주세요!")
    private String description;
}
