package com.malgn.malgncms.domain.entity;

import com.malgn.malgncms.contetns.dto.ContentsRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
 * fileName       : Contents
 * author         : JAEIK
 * date           : 3/6/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/6/26        JAEIK       최초 생성
 */
@Entity
@Table(name = "contents")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Builder.Default
    @Column(name = "view_count")
    private Long viewCount = 0L;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "created_by")
    private String createBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    public Content(String title, String description, Long viewCount) {
        this.title = title;
        this.description = description;
        this.viewCount = viewCount;
        this.createdDate = Instant.now(); // 테스트용 기본값
    }

    public static Content toEntity(String username, ContentsRequest contentsRequest) {
        return Content.builder()
                .title(contentsRequest.getTitle())
                .description(contentsRequest.getDescription())
                .createdDate(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toInstant())
                .createBy(username)
                .build();
    }
}
