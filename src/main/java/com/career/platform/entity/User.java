package com.career.platform.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    private String role; // STUDENT, ADMIN, COUNSELLOR

    private String skills; // Comma-separated list of selected skills and interests

    // ── Constructors ──────────────────────────────────────────────────────────
    public User() {}

    public User(Long id, String username, String email, String password, String role, String skills) {
        this.id       = id;
        this.username = username;
        this.email    = email;
        this.password = password;
        this.role     = role;
        this.skills   = skills;
    }

    // ── Builder ───────────────────────────────────────────────────────────────
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long   id;
        private String username;
        private String email;
        private String password;
        private String role;
        private String skills;

        public Builder id(Long id)             { this.id = id; return this; }
        public Builder username(String u)      { this.username = u; return this; }
        public Builder email(String e)         { this.email = e; return this; }
        public Builder password(String p)      { this.password = p; return this; }
        public Builder role(String r)          { this.role = r; return this; }
        public Builder skills(String s)        { this.skills = s; return this; }

        public User build() {
            return new User(id, username, email, password, role, skills);
        }
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────
    public Long   getId()       { return id; }
    public void   setId(Long id){ this.id = id; }

    public String getUsername()          { return username; }
    public void   setUsername(String u)  { this.username = u; }

    public String getEmail()             { return email; }
    public void   setEmail(String e)     { this.email = e; }

    public String getPassword()          { return password; }
    public void   setPassword(String p)  { this.password = p; }

    public String getRole()              { return role; }
    public void   setRole(String r)      { this.role = r; }

    public String getSkills()            { return skills; }
    public void   setSkills(String s)    { this.skills = s; }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', email='" + email +
               "', role='" + role + "'}";
    }
}
