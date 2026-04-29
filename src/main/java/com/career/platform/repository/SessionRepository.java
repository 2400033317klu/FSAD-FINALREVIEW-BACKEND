package com.career.platform.repository;

import com.career.platform.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByStudentId(Long studentId);
    List<Session> findByCounsellorId(Long counsellorId);
}
