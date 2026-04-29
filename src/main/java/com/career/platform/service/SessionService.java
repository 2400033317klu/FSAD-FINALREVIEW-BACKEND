package com.career.platform.service;

import com.career.platform.entity.Session;
import com.career.platform.repository.SessionRepository;
import com.career.platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    public Session bookSession(Session session) {
        userRepository.findById(session.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + session.getStudentId()));
        if (session.getCounsellorId() != null) {
            userRepository.findById(session.getCounsellorId())
                    .orElseThrow(() -> new RuntimeException("Counsellor not found with id: " + session.getCounsellorId()));
        }
        session.setStatus("SCHEDULED");
        return sessionRepository.save(session);
    }

    /** Returns all sessions in the system. */
    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    /** Returns sessions for a specific student. */
    public List<Session> getSessionsByStudent(Long studentId) {
        return sessionRepository.findByStudentId(studentId);
    }

    /** Returns sessions for a specific counsellor. */
    public List<Session> getSessionsByCounsellor(Long counsellorId) {
        return sessionRepository.findByCounsellorId(counsellorId);
    }


}
