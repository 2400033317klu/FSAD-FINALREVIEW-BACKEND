package com.career.platform.controller;

import com.career.platform.entity.Appointment;
import com.career.platform.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:5175"})
public class AppointmentController {

    @Autowired
    private AppointmentService service;

    @GetMapping("/student/{studentId}")
    public List<Appointment> getAppointments(@PathVariable Long studentId) {
        return service.getAppointmentsByStudentId(studentId);
    }

    @PostMapping
    public Appointment bookAppointment(@RequestBody Appointment appointment) {
        return service.bookAppointment(appointment);
    }
}
