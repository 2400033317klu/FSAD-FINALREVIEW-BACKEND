package com.career.platform.service;

import com.career.platform.entity.Appointment;
import com.career.platform.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository repository;

    public List<Appointment> getAppointmentsByStudentId(Long studentId) {
        return repository.findByStudentId(studentId);
    }

    public Appointment bookAppointment(Appointment appointment) {
        appointment.setStatus("SCHEDULED");
        return repository.save(appointment);
    }
}
