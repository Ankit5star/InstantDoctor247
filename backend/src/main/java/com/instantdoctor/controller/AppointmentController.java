package com.instantdoctor.controller;

import com.instantdoctor.entity.*;
import com.instantdoctor.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final WalletRepository walletRepository;
    private final WalletTransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public AppointmentController(AppointmentRepository appointmentRepository,
                                 DoctorRepository doctorRepository,
                                 WalletRepository walletRepository,
                                 WalletTransactionRepository transactionRepository,
                                 UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Appointment> getAppointments(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Long doctorId) {
        if (email != null && !email.isBlank()) {
            String cleanEmail = email.trim().toLowerCase();
            if (status != null && !status.isBlank() && !status.equalsIgnoreCase("ALL")) {
                return appointmentRepository.findByPatientEmailAndStatusIgnoreCaseOrderByCreatedAtDesc(cleanEmail, status);
            }
            return appointmentRepository.findByPatientEmailOrderByCreatedAtDesc(cleanEmail);
        }
        if (doctorId != null) {
            return appointmentRepository.findByDoctorIdOrderByCreatedAtDesc(doctorId);
        }
        if (status != null && !status.isBlank() && !status.equalsIgnoreCase("ALL")) {
            return appointmentRepository.findByStatusIgnoreCase(status);
        }
        return appointmentRepository.findAllByOrderByCreatedAtDesc();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        return appointmentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        if (appointment.getStatus() == null || appointment.getStatus().isBlank()) {
            appointment.setStatus("UPCOMING");
        }
        if (appointment.getAppointmentNumber() == null) {
            appointment.setAppointmentNumber("APT-" + System.currentTimeMillis());
        }
        if (appointment.getPatientEmail() != null) {
            appointment.setPatientEmail(appointment.getPatientEmail().trim().toLowerCase());
        }

        Appointment saved = appointmentRepository.save(appointment);

        // Process wallet transaction if patient pays via Wallet
        if (appointment.getPaymentMethod() != null && 
            appointment.getPaymentMethod().toLowerCase().contains("wallet") && 
            appointment.getFee() != null && appointment.getFee() > 0) {
            Long userId = appointment.getUserId();
            if (userId == null && appointment.getPatientEmail() != null) {
                userRepository.findByEmail(appointment.getPatientEmail()).ifPresent(u -> {
                    debitPatientWallet(u.getId(), appointment.getFee(), appointment.getDoctorName());
                });
            } else if (userId != null) {
                debitPatientWallet(userId, appointment.getFee(), appointment.getDoctorName());
            }
        }

        return saved;
    }

    private void debitPatientWallet(Long userId, Double fee, String doctorName) {
        walletRepository.findByUserId(userId).ifPresent(wallet -> {
            if (wallet.getBalance() >= fee) {
                wallet.setBalance(wallet.getBalance() - fee);
                wallet.setUpdatedAt(LocalDateTime.now());
                walletRepository.save(wallet);

                WalletTransaction tx = new WalletTransaction(
                        null, userId, fee, "DEBIT", 
                        "Appointment Consultation - " + (doctorName != null ? doctorName : "Doctor"), 
                        "CONSULTATION", "CONSULTATION"
                );
                transactionRepository.save(tx);
            }
        });
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Appointment> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return appointmentRepository.findById(id).map(existing -> {
            existing.setStatus(status.toUpperCase());
            return ResponseEntity.ok(appointmentRepository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }
}
