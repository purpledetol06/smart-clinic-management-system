package com.smartclinic.controller;

import com.smartclinic.entity.Doctor;
import com.smartclinic.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/availability")
    public ResponseEntity<?> getDoctorAvailability(
            @RequestParam String token,
            @RequestParam(required = false) String speciality,
            @RequestParam(required = false) String time) {
        try {
            if (token == null || token.isEmpty()) {
                return ResponseEntity.status(401).body(Map.of("error", "Invalid token"));
            }
            List<Doctor> doctors = doctorService.getAvailableDoctors(speciality, time);
            return ResponseEntity.ok(doctors);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllDoctors() {
        try {
            List<Doctor> doctors = doctorService.getAllDoctors();
            return ResponseEntity.ok(doctors);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> doctorLogin(
            @RequestBody Map<String, String> loginRequest) {
        try {
            String email = loginRequest.get("email");
            String password = loginRequest.get("password");
            Doctor doctor = doctorService.authenticateDoctor(email, password);
            if (doctor != null) {
                return ResponseEntity.ok(Map.of("message", "Login successful", "doctorId", doctor.getDoctorId()));
            }
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}
