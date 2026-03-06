package com.malgn.malgncms.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
public class Contents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Min(0)
    @NotNull
    @Column(name = "view_count")
    private Long viewCount;

    @Column(name = "created_date")
    private Instant createdDate;

    @NotBlank
    @Size(max = 50)
    @Column(name = "created_by")
    private String createBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Size(max = 50)
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

}
