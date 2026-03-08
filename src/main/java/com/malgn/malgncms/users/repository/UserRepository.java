package com.malgn.malgncms.users.repository;

import com.malgn.malgncms.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * packageName    : com.malgn.malgncms.users.repository
 * fileName       : UserRepository
 * author         : JAEIK
 * date           : 3/8/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/8/26        JAEIK       최초 생성
 */
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
