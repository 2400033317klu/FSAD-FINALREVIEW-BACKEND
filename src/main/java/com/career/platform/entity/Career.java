package com.career.platform.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "careers")
public class Career {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "skills_required")
    private String skillsRequired;

    private String category; // Tech, Design, Business

    // ── Constructors ──────────────────────────────────────────────────────────
    public Career() {}

    public Career(Long id, String title, String description, String skillsRequired, String category) {
        this.id             = id;
        this.title          = title;
        this.description    = description;
        this.skillsRequired = skillsRequired;
        this.category       = category;
    }

    // ── Builder ───────────────────────────────────────────────────────────────
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long   id;
        private String title;
        private String description;
        private String skillsRequired;
        private String category;

        public Builder id(Long id)                     { this.id = id; return this; }
        public Builder title(String t)                 { this.title = t; return this; }
        public Builder description(String d)           { this.description = d; return this; }
        public Builder skillsRequired(String s)        { this.skillsRequired = s; return this; }
        public Builder category(String c)              { this.category = c; return this; }

        public Career build() {
            return new Career(id, title, description, skillsRequired, category);
        }
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────
    public Long   getId()                        { return id; }
    public void   setId(Long id)                 { this.id = id; }

    public String getTitle()                     { return title; }
    public void   setTitle(String t)             { this.title = t; }

    public String getDescription()               { return description; }
    public void   setDescription(String d)       { this.description = d; }

    public String getSkillsRequired()            { return skillsRequired; }
    public void   setSkillsRequired(String s)    { this.skillsRequired = s; }

    public String getCategory()                  { return category; }
    public void   setCategory(String c)          { this.category = c; }

    @Override
    public String toString() {
        return "Career{id=" + id + ", title='" + title + "', category='" + category + "'}";
    }
}
