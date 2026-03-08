package com.malgn.malgncms.contetns.dto;

import com.malgn.malgncms.domain.entity.Content;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * packageName    : com.malgn.malgncms.contetns.dto
 * fileName       : ContetsResponse
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
public class ContentsResponse {
    private String title;
    private String description;
    private Long viewCount;
    private Instant createdDate;
    private String createdBy;
    private Instant lastModifiedDate;
    private String lastModifiedBy;

    public static ContentsResponse toDto(Content content) {
        return ContentsResponse.builder()
                .title(content.getTitle())
                .description(content.getDescription())
                .viewCount(content.getViewCount())
                .createdDate(content.getCreatedDate())
                .createdBy(content.getCreateBy())
                .lastModifiedDate(content.getLastModifiedDate())
                .lastModifiedBy(content.getLastModifiedBy())
                .build();
    }
}


