package com.malgn.malgncms.auth.repository;

import com.malgn.malgncms.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * packageName    : com.malgn.malgncms.auth.repository
 * fileName       : AuthRepository
 * author         : JAEIK
 * date           : 3/8/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/8/26        JAEIK       최초 생성
 */
public interface AuthRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
}
