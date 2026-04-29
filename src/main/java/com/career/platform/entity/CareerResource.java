package com.career.platform.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "career_resources")
public class CareerResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String category;
    private String link;
    private Double rating;

    // ── Constructors ──────────────────────────────────────────────────────────
    public CareerResource() {}

    public CareerResource(Long id, String title, String description,
                          String category, String link, Double rating) {
        this.id          = id;
        this.title       = title;
        this.description = description;
        this.category    = category;
        this.link        = link;
        this.rating      = rating;
    }

    // ── Builder ───────────────────────────────────────────────────────────────
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long   id;
        private String title;
        private String description;
        private String category;
        private String link;
        private Double rating;

        public Builder id(Long id)           { this.id = id; return this; }
        public Builder title(String t)       { this.title = t; return this; }
        public Builder description(String d) { this.description = d; return this; }
        public Builder category(String c)    { this.category = c; return this; }
        public Builder link(String l)        { this.link = l; return this; }
        public Builder rating(double r)      { this.rating = r; return this; }

        public CareerResource build() {
            return new CareerResource(id, title, description, category, link, rating);
        }
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────
    public Long   getId()                    { return id; }
    public void   setId(Long id)             { this.id = id; }

    public String getTitle()                 { return title; }
    public void   setTitle(String t)         { this.title = t; }

    public String getDescription()           { return description; }
    public void   setDescription(String d)   { this.description = d; }

    public String getCategory()              { return category; }
    public void   setCategory(String c)      { this.category = c; }

    public String getLink()                  { return link; }
    public void   setLink(String l)          { this.link = l; }

    public Double getRating()                { return rating; }
    public void   setRating(Double r)        { this.rating = r; }

    @Override
    public String toString() {
        return "CareerResource{id=" + id + ", title='" + title + "', category='" + category + "'}";
    }
}
