package com.smartclinic.service;

import com.smartclinic.entity.Appointment;
import com.smartclinic.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment bookAppointment(Appointment appointment) {
        appointment.setStatus("SCHEDULED");
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsByDoctor(Integer doctorId, java.time.LocalDate date) {
        return appointmentRepository.findAppointmentsByDoctorAndDate(doctorId, date);
    }

    public List<Appointment> getAppointmentsByPatient(Integer patientId) {
        return appointmentRepository.findAppointmentsByPatient(patientId);
    }

    public Appointment updateAppointmentStatus(Integer appointmentId, String status) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
        if (appointment != null) {
            appointment.setStatus(status);
            return appointmentRepository.save(appointment);
        }
        return null;
    }
}
