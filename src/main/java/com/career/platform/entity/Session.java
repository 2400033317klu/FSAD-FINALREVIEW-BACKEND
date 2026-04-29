package com.career.platform.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long studentId;

    @Column(nullable = true)
    private Long counsellorId;

    /** Expected format: yyyy-MM-dd */
    @Column(nullable = false)
    private String date;

    /** SCHEDULED | COMPLETED | CANCELLED */
    @Column(nullable = false)
    private String status;

    // ── Constructors ──────────────────────────────────────────────────────────
    public Session() {}

    public Session(Long id, Long studentId, Long counsellorId, String date, String status) {
        this.id          = id;
        this.studentId   = studentId;
        this.counsellorId = counsellorId;
        this.date        = date;
        this.status      = status;
    }

    // ── Builder ───────────────────────────────────────────────────────────────
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long   id;
        private Long   studentId;
        private Long   counsellorId;
        private String date;
        private String status;

        public Builder id(Long id)               { this.id = id; return this; }
        public Builder studentId(Long s)         { this.studentId = s; return this; }
        public Builder counsellorId(Long c)      { this.counsellorId = c; return this; }
        public Builder date(String d)            { this.date = d; return this; }
        public Builder status(String s)          { this.status = s; return this; }

        public Session build() {
            return new Session(id, studentId, counsellorId, date, status);
        }
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────
    public Long   getId()                    { return id; }
    public void   setId(Long id)             { this.id = id; }

    public Long   getStudentId()             { return studentId; }
    public void   setStudentId(Long s)       { this.studentId = s; }

    public Long   getCounsellorId()          { return counsellorId; }
    public void   setCounsellorId(Long c)    { this.counsellorId = c; }

    public String getDate()                  { return date; }
    public void   setDate(String d)          { this.date = d; }

    public String getStatus()                { return status; }
    public void   setStatus(String s)        { this.status = s; }

    @Override
    public String toString() {
        return "Session{id=" + id + ", studentId=" + studentId +
               ", counsellorId=" + counsellorId + ", date='" + date +
               "', status='" + status + "'}";
    }
}
