package com.career.platform.controller;

import com.career.platform.entity.User;
import com.career.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:5175"})
public class UserController {

    @Autowired
    private UserService userService;

    /** POST /api/register */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.registerUser(user));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /** POST /api/login */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        Optional<User> user = userService.loginUser(
                loginRequest.getUsername(), loginRequest.getPassword());
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.status(401).body("Invalid username or password");
    }

    /** GET /api/users — list all users (admin use) */
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /** GET /api/admin/normalize-counsellor-roles */
    @GetMapping("/admin/normalize-counsellor-roles")
    public ResponseEntity<?> normalizeCounsellorRoles() {
        int updated = userService.normalizeCounsellorRoles();
        return ResponseEntity.ok("Normalized " + updated + " counsellor role(s)");
    }
}
