package com.instantdoctor.controller;

import com.instantdoctor.dto.ApiResponse;
import com.instantdoctor.entity.Doctor;
import com.instantdoctor.repository.AppointmentRepository;
import com.instantdoctor.repository.DoctorRepository;
import com.instantdoctor.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public AdminController(DoctorRepository doctorRepository,
                           AppointmentRepository appointmentRepository,
                           UserRepository userRepository) {
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/metrics")
    public ResponseEntity<?> getPlatformMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("totalUsers", userRepository.count());
        metrics.put("totalDoctors", doctorRepository.count());
        metrics.put("totalAppointments", appointmentRepository.count());
        metrics.put("pendingKyc", doctorRepository.findByKycStatus("PENDING").size());
        metrics.put("grossRevenue", 78450.00);

        return ResponseEntity.ok(ApiResponse.ok(metrics));
    }

    @GetMapping("/doctors/pending")
    public ResponseEntity<?> getPendingDoctors() {
        return ResponseEntity.ok(ApiResponse.ok(doctorRepository.findByKycStatus("PENDING")));
    }

    @PatchMapping("/doctors/{id}/status")
    public ResponseEntity<?> updateDoctorStatus(@PathVariable Long id, @RequestParam String status) {
        return doctorRepository.findById(id).map(doc -> {
            doc.setKycStatus(status.toUpperCase());
            doctorRepository.save(doc);
            return ResponseEntity.ok(ApiResponse.ok("Doctor KYC status updated to " + status, doc));
        }).orElse(ResponseEntity.notFound().build());
    }
}
