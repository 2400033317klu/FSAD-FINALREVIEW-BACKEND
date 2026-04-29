package com.career.platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // Auth — all sub-paths (register, login, profile, counsellors)
                .requestMatchers("/api/auth/**").permitAll()
                // Top-level register / login aliases
                .requestMatchers(HttpMethod.POST, "/api/register", "/api/login").permitAll()
                // Career resources
                .requestMatchers("/api/career-resources/**").permitAll()
                // Careers
                .requestMatchers("/api/careers/**").permitAll()
                // Sessions
                .requestMatchers("/api/book-session").permitAll()
                .requestMatchers("/api/sessions").permitAll()
                .requestMatchers("/api/sessions/**").permitAll()
                .requestMatchers("/api/counsellors/**").permitAll()
                // Users / admin utilities
                .requestMatchers(HttpMethod.GET, "/api/users").permitAll()
                .requestMatchers("/api/admin/**").permitAll()
                // Appointments
                .requestMatchers("/api/appointments/**").permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(basic -> basic.disable())
            .formLogin(form -> form.disable());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173",
                "http://localhost:5174",
                "http://localhost:5175"
        ));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList(
                "Content-Type", "Authorization", "X-Username", "X-Requested-With", "Accept"
        ));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
