package com.career.platform.controller;

import com.career.platform.entity.Career;
import com.career.platform.service.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/careers")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:5175"})
public class CareerController {

    @Autowired
    private CareerService careerService;

    @GetMapping
    public List<Career> getAllCareers() {
        return careerService.getAllCareers();
    }

    /**
     * POST /api/careers
     * Requires the caller's username in the X-Username header.
     * Only ADMIN users are permitted to create careers.
     */
    @PostMapping
    public ResponseEntity<?> addCareer(
            @RequestBody Career career,
            @RequestHeader(value = "X-Username", required = false) String username) {
        if (username == null || username.isBlank()) {
            return ResponseEntity.status(401).body("Authentication required: X-Username header missing.");
        }
        try {
            return ResponseEntity.ok(careerService.addCareer(career, username));
        } catch (SecurityException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
