package com.career.platform.service;

import com.career.platform.entity.User;
import com.career.platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(normalizeRole(user.getRole()));
        return userRepository.save(user);
    }

    public Optional<User> loginUser(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
            User user = userOpt.get();
            String normalizedRole = normalizeRole(user.getRole());
            if (!normalizedRole.equals(user.getRole())) {
                user.setRole(normalizedRole);
                userRepository.save(user);
            }
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public User updateUserSkills(String username, String skills) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setSkills(skills);
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found");
    }

    public List<User> getUsersByRole(String role) {
        String normalizedRole = normalizeRole(role);
        if ("COUNSELLOR".equals(normalizedRole)) {
            List<User> counsellors = new ArrayList<>(userRepository.findByRole("COUNSELLOR"));
            counsellors.addAll(userRepository.findByRole("COUNSELOR"));
            return counsellors;
        }
        return userRepository.findByRole(normalizedRole);
    }

    public List<User> getCounsellors() {
        List<User> list = userRepository.findAllCounsellors();
        System.out.println("Counsellors count: " + list.size());
        list.forEach(u -> System.out.println("  -> id=" + u.getId() + " username=" + u.getUsername() + " role='" + u.getRole() + "'"));
        return list;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public int normalizeCounsellorRoles() {
        List<User> allCounsellors = userRepository.findAllCounsellors();
        int count = 0;
        for (User user : allCounsellors) {
            if (!"COUNSELLOR".equals(user.getRole())) {
                user.setRole("COUNSELLOR");
                userRepository.save(user);
                count++;
            }
        }
        return count;
    }

    public boolean isStudent(User user) {
        return "STUDENT".equals(normalizeRole(user.getRole()));
    }

    public boolean isCounsellor(User user) {
        return "COUNSELLOR".equals(normalizeRole(user.getRole()));
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User updateProfileData(String username, User updatedUser) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (updatedUser.getEmail() != null) user.setEmail(updatedUser.getEmail());
            if (updatedUser.getSkills() != null) user.setSkills(updatedUser.getSkills());
            // Role and Username are typically not changeable by the user directly
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found");
    }

    private String normalizeRole(String role) {
        if (role == null) {
            return "STUDENT";
        }
        String normalized = role.trim().toUpperCase();
        if ("COUNSELOR".equals(normalized)) {
            return "COUNSELLOR";
        }
        if ("COUNSELLOR".equals(normalized) || "ADMIN".equals(normalized) || "STUDENT".equals(normalized)) {
            return normalized;
        }
        return "STUDENT";
    }
}
