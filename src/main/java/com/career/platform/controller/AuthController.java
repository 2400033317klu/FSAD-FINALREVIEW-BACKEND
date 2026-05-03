package com.career.platform.controller;

import com.career.platform.entity.User;
import com.career.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:5175"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class AuthController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/counsellors")
    public ResponseEntity<List<User>> getCounsellors() {
        return ResponseEntity.ok(userService.getUsersByRole("COUNSELLOR"));
    }

    @PostMapping("/profile/skills")
    public ResponseEntity<?> updateSkills(@RequestBody java.util.Map<String, String> request) {
        try {
            String username = request.get("username");
            String skills = request.get("skills");
            
            if (username == null || username.isEmpty()) {
                return ResponseEntity.badRequest().body("Username is required");
            }
            
            return ResponseEntity.ok(userService.updateUserSkills(username, skills));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.registerUser(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.loginUser(user.getUsername(), user.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<?> getProfile(@PathVariable String username) {
        Optional<User> user = userService.getUserByUsername(username);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody User userRequest) {
        try {
            return ResponseEntity.ok(userService.updateProfileData(userRequest.getUsername(), userRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
