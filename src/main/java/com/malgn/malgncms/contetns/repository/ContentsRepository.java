package com.malgn.malgncms.contetns.repository;

import com.malgn.malgncms.domain.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.malgn.malgncms.contetns.repository
 * fileName       : ContentsRepository
 * author         : JAEIK
 * date           : 3/9/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/9/26        JAEIK       최초 생성
 */
public interface ContentsRepository extends JpaRepository<Content, Long> {
}
