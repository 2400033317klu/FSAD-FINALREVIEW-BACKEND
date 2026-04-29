package com.career.platform.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long   studentId;
    private String counselorName;
    private String topic;
    private String date;
    private String time;
    private String status; // SCHEDULED, COMPLETED, CANCELLED

    // ── Constructors ──────────────────────────────────────────────────────────
    public Appointment() {}

    public Appointment(Long id, Long studentId, String counselorName,
                       String topic, String date, String time, String status) {
        this.id           = id;
        this.studentId    = studentId;
        this.counselorName = counselorName;
        this.topic        = topic;
        this.date         = date;
        this.time         = time;
        this.status       = status;
    }

    // ── Builder ───────────────────────────────────────────────────────────────
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long   id;
        private Long   studentId;
        private String counselorName;
        private String topic;
        private String date;
        private String time;
        private String status;

        public Builder id(Long id)                 { this.id = id; return this; }
        public Builder studentId(Long s)           { this.studentId = s; return this; }
        public Builder counselorName(String c)     { this.counselorName = c; return this; }
        public Builder topic(String t)             { this.topic = t; return this; }
        public Builder date(String d)              { this.date = d; return this; }
        public Builder time(String t)              { this.time = t; return this; }
        public Builder status(String s)            { this.status = s; return this; }

        public Appointment build() {
            return new Appointment(id, studentId, counselorName, topic, date, time, status);
        }
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────
    public Long   getId()                        { return id; }
    public void   setId(Long id)                 { this.id = id; }

    public Long   getStudentId()                 { return studentId; }
    public void   setStudentId(Long s)           { this.studentId = s; }

    public String getCounselorName()             { return counselorName; }
    public void   setCounselorName(String c)     { this.counselorName = c; }

    public String getTopic()                     { return topic; }
    public void   setTopic(String t)             { this.topic = t; }

    public String getDate()                      { return date; }
    public void   setDate(String d)              { this.date = d; }

    public String getTime()                      { return time; }
    public void   setTime(String t)              { this.time = t; }

    public String getStatus()                    { return status; }
    public void   setStatus(String s)            { this.status = s; }

    @Override
    public String toString() {
        return "Appointment{id=" + id + ", studentId=" + studentId +
               ", counselorName='" + counselorName + "', topic='" + topic +
               "', date='" + date + "', status='" + status + "'}";
    }
}
