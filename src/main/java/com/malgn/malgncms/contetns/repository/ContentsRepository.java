package com.malgn.malgncms.contetns.repository;

import com.malgn.malgncms.domain.entity.Content;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

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
    Page<Content> findAll(Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Content c where c.id = :id")
    Optional<Content> findByIdWithLock(@Param("id") Long id);
}
