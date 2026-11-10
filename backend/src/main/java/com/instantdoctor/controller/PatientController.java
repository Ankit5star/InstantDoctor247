package com.instantdoctor.controller;

import com.instantdoctor.dto.ApiResponse;
import com.instantdoctor.entity.Patient;
import com.instantdoctor.entity.User;
import com.instantdoctor.repository.PatientRepository;
import com.instantdoctor.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public PatientController(PatientRepository patientRepository, UserRepository userRepository) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getProfile(@PathVariable Long userId) {
        Patient patient = patientRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId).orElse(null);
                    if (user == null) return null;
                    Patient newP = new Patient();
                    newP.setUser(user);
                    newP.setFullName(user.getEmail().split("@")[0]);
                    newP.setAvatarUrl("https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150&auto=format&fit=crop&q=80");
                    return patientRepository.save(newP);
                });

        if (patient == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ApiResponse.ok(patient));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateProfile(@PathVariable Long userId, @RequestBody Map<String, Object> body) {
        Patient patient = patientRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId).orElse(null);
                    if (user == null) return null;
                    Patient newP = new Patient();
                    newP.setUser(user);
                    return newP;
                });

        if (patient == null) {
            return ResponseEntity.notFound().build();
        }

        if (body.containsKey("name") || body.containsKey("fullName")) {
            Object val = body.get("name") != null ? body.get("name") : body.get("fullName");
            patient.setFullName(String.valueOf(val));
        }
        if (body.containsKey("phone")) {
            patient.setPhone(String.valueOf(body.get("phone")));
        }
        if (body.containsKey("gender")) {
            patient.setGender(String.valueOf(body.get("gender")));
        }
        if (body.containsKey("bloodGroup")) {
            patient.setBloodGroup(String.valueOf(body.get("bloodGroup")));
        }
        if (body.containsKey("dob")) {
            patient.setDob(String.valueOf(body.get("dob")));
        }
        if (body.containsKey("emergencyContact")) {
            patient.setEmergencyContact(String.valueOf(body.get("emergencyContact")));
        }
        if (body.containsKey("avatarUrl")) {
            patient.setAvatarUrl(String.valueOf(body.get("avatarUrl")));
        }

        Patient saved = patientRepository.save(patient);
        return ResponseEntity.ok(ApiResponse.ok("Profile updated successfully", saved));
    }
}
