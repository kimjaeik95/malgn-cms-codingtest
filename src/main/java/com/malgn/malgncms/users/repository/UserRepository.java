package com.malgn.malgncms.users.repository;

import com.malgn.malgncms.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

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
public interface UserRepository extends JpaRepository<Users, Long> {
    boolean existsByUsername(String username);
}
