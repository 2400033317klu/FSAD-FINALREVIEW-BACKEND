package com.career.platform.service;

import com.career.platform.entity.Career;
import com.career.platform.entity.User;
import com.career.platform.repository.CareerRepository;
import com.career.platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareerService {

    @Autowired
    private CareerRepository careerRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Career> getAllCareers() {
        return careerRepository.findAll();
    }

    /**
     * Only users with role ADMIN may create a career entry.
     *
     * @param career   the career to persist
     * @param username the username of the requesting user
     * @return the saved Career
     * @throws SecurityException   if the user is not an ADMIN
     * @throws RuntimeException    if the user is not found
     */
    public Career addCareer(Career career, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            throw new SecurityException("Access denied: only ADMIN users can add careers.");
        }

        return careerRepository.save(career);
    }
}
