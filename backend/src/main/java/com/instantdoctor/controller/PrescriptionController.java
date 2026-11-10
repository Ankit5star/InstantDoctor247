package com.instantdoctor.controller;

import com.instantdoctor.dto.ApiResponse;
import com.instantdoctor.entity.Prescription;
import com.instantdoctor.repository.PrescriptionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionController(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<?> getPrescriptionByAppointment(@PathVariable Long appointmentId) {
        return prescriptionRepository.findByAppointmentId(appointmentId)
                .map(p -> ResponseEntity.ok(ApiResponse.ok(p)))
                .orElse(ResponseEntity.ok(ApiResponse.ok("Prescription pending consultation completion", null)));
    }

    @PostMapping
    public ResponseEntity<?> createPrescription(@RequestBody Prescription prescription) {
        Prescription saved = prescriptionRepository.save(prescription);
        return ResponseEntity.ok(ApiResponse.ok("Prescription created successfully", saved));
    }
}
