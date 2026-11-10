package com.instantdoctor.controller;

import com.instantdoctor.entity.Doctor;
import com.instantdoctor.repository.DoctorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorRepository doctorRepository;

    public DoctorController(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @GetMapping
    public List<Doctor> getDoctors(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) Boolean instant) {
        if (search != null && !search.isBlank()) {
            return doctorRepository.findByNameContainingIgnoreCase(search);
        }
        if (specialty != null && !specialty.isBlank() && !specialty.equalsIgnoreCase("All")) {
            return doctorRepository.findBySpecialtyIgnoreCase(specialty);
        }
        if (Boolean.TRUE.equals(instant)) {
            return doctorRepository.findByIsAvailableInstantTrue();
        }
        return doctorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        return doctorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Doctor> getDoctorByUserId(@PathVariable Long userId) {
        return doctorRepository.findByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/availability")
    public ResponseEntity<Doctor> toggleAvailability(@PathVariable Long id, @RequestParam Boolean available) {
        return doctorRepository.findById(id).map(doc -> {
            doc.setIsAvailableInstant(available);
            return ResponseEntity.ok(doctorRepository.save(doc));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return doctorRepository.save(doctor);
    }
}
