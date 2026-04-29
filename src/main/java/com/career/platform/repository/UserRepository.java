package com.career.platform.repository;

import com.career.platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByRole(String role);

    /** Finds all counsellors regardless of COUNSELLOR vs COUNSELOR spelling stored in DB */
    @Query("SELECT u FROM User u WHERE UPPER(TRIM(u.role)) IN ('COUNSELLOR', 'COUNSELOR')")
    List<User> findAllCounsellors();
}
