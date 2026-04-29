package com.career.platform.controller;

import com.career.platform.entity.Session;
import com.career.platform.entity.User;
import com.career.platform.service.SessionService;
import com.career.platform.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:5175"})
public class SessionController {

    private static final Logger logger = LoggerFactory.getLogger(SessionController.class);

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;

    /** GET /api/counsellors — list of users with COUNSELLOR role for student booking dropdown */
    @GetMapping("/counsellors")
    public ResponseEntity<List<User>> getCounsellors() {
        List<User> counsellors = userService.getCounsellors();
        logger.info("GET /api/counsellors returned {} counsellor(s)", counsellors.size());
        return ResponseEntity.ok(counsellors);
    }

    /** POST /api/book-session — only STUDENT can book; requires studentId, counsellorId, date */
    @PostMapping("/book-session")
    public ResponseEntity<?> bookSession(
            @RequestBody Session session,
            @RequestHeader(value = "X-Username", required = false) String username) {
        if (username == null || username.isBlank()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Authentication required to book sessions.");
        }
        var userOpt = userService.getUserByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Authenticated user not found.");
        }
        var user = userOpt.get();
        if (!userService.isStudent(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only students can book sessions.");
        }
        if (session.getStudentId() == null || !session.getStudentId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Students may only book sessions for their own account.");
        }
        if (session.getCounsellorId() == null) {
            return ResponseEntity.badRequest().body("Counsellor ID is required.");
        }
        try {
            return ResponseEntity.ok(sessionService.bookSession(session));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /** GET /api/sessions/student/{studentId} */
    @GetMapping("/sessions/student/{studentId}")
    public ResponseEntity<List<Session>> getSessionsByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(sessionService.getSessionsByStudent(studentId));
    }

    /** GET /api/sessions/counsellor/{counsellorId} */
    @GetMapping("/sessions/counsellor/{counsellorId}")
    public ResponseEntity<List<Session>> getSessionsByCounsellor(@PathVariable Long counsellorId) {
        return ResponseEntity.ok(sessionService.getSessionsByCounsellor(counsellorId));
    }

    /** GET /api/sessions — optional ?studentId query param (kept for backward compat) */
    @GetMapping("/sessions")
    public ResponseEntity<List<Session>> getSessions(
            @RequestParam(required = false) Long studentId) {
        if (studentId != null) {
            return ResponseEntity.ok(sessionService.getSessionsByStudent(studentId));
        }
        return ResponseEntity.ok(sessionService.getAllSessions());
    }
}
