package com.instantdoctor.config;

import com.instantdoctor.entity.*;
import com.instantdoctor.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initData(UserRepository userRepo,
                              DoctorRepository doctorRepo,
                              AppointmentRepository apptRepo,
                              WalletRepository walletRepo,
                              WalletTransactionRepository txRepo,
                              PrescriptionRepository rxRepo,
                              PasswordEncoder passwordEncoder) {
        return args -> {
            // Seed Users if empty
            if (userRepo.count() == 0) {
                User patientUser = new User(null, "vedant.tidke@instantdoctor.com", passwordEncoder.encode("Password@123"), Role.ROLE_PATIENT);
                User doctorUser = new User(null, "dr.crick@instantdoctor.com", passwordEncoder.encode("Password@123"), Role.ROLE_DOCTOR);
                User adminUser = new User(null, "admin@instantdoctor.com", passwordEncoder.encode("Admin@123"), Role.ROLE_ADMIN);

                userRepo.saveAll(List.of(patientUser, doctorUser, adminUser));

                // Seed Wallets
                Wallet w1 = new Wallet(null, patientUser.getId(), 450.00);
                Wallet w2 = new Wallet(null, doctorUser.getId(), 1420.00);
                walletRepo.saveAll(List.of(w1, w2));

                // Seed Transactions
                WalletTransaction tx1 = new WalletTransaction(null, patientUser.getId(), 200.0, "DEBIT", "Consultation - Dr. Emma Mia", "General Physician", "CONSULTATION");
                WalletTransaction tx2 = new WalletTransaction(null, patientUser.getId(), 250.0, "DEBIT", "Consultation - Dr. Phillip Rosser", "Cardiologist", "CONSULTATION");
                WalletTransaction tx3 = new WalletTransaction(null, patientUser.getId(), 500.0, "CREDIT", "Wallet Top-up via Credit Card", "Visa ****4242", "DEPOSIT");
                txRepo.saveAll(List.of(tx1, tx2, tx3));
            }

            // Seed Doctors if empty
            if (doctorRepo.count() == 0) {
                Doctor d1 = new Doctor(null, "Dr. Crick", "Cardiologist", 3.7, 120, 25.0, 8, 1240,
                        "Specialized in cardiovascular health and preventative care. Over 8 years of clinical experience in top medical centers.",
                        "https://images.unsplash.com/photo-1622253692010-333f2da6031d?w=300&auto=format&fit=crop&q=80",
                        "New York, NY", true);
                d1.setKycStatus("VERIFIED");

                Doctor d2 = new Doctor(null, "Dr. Lachinet", "Dentist", 2.9, 85, 29.0, 6, 890,
                        "Expert oral surgeon and preventive dental practitioner with gentle patient care philosophy.",
                        "https://images.unsplash.com/photo-1594824813524-8b63e80f2d4e?w=300&auto=format&fit=crop&q=80",
                        "Boston, MA", true);
                d2.setKycStatus("VERIFIED");

                Doctor d3 = new Doctor(null, "Dr. Strain", "General Physician", 3.0, 210, 22.0, 10, 2150,
                        "Primary care and internal medicine specialist focused on comprehensive holistic health and telehealth.",
                        "https://images.unsplash.com/photo-1612349317150-e413f6a5b16d?w=300&auto=format&fit=crop&q=80",
                        "Chicago, IL", true);
                d3.setKycStatus("VERIFIED");

                Doctor d4 = new Doctor(null, "Dr. Sarah Jenkins", "Pediatrician", 4.9, 340, 32.0, 12, 3400,
                        "Caring pediatrician specializing in infant care, childhood development, and immunizations.",
                        "https://images.unsplash.com/photo-1559839734-2b71ea197ec2?w=300&auto=format&fit=crop&q=80",
                        "San Francisco, CA", true);
                d4.setKycStatus("VERIFIED");

                // Pending KYC Doctor for Admin verification flow
                Doctor d5 = new Doctor(null, "Dr. Jonathan Hayes", "Neurologist", 4.8, 15, 45.0, 14, 320,
                        "Board-certified neurologist specializing in neuro-rehabilitation and chronic migraine disorders.",
                        "https://images.unsplash.com/photo-1537368910025-700350fe46c7?w=300&auto=format&fit=crop&q=80",
                        "Seattle, WA", false);
                d5.setKycStatus("PENDING");
                d5.setNicFrontUrl("NIC_Front_Scan.pdf");
                d5.setNicBackUrl("NIC_Back_Scan.pdf");
                d5.setDegreeUrl("Johns_Hopkins_MD.pdf");

                doctorRepo.saveAll(List.of(d1, d2, d3, d4, d5));
            }

            // Seed Appointments & Prescriptions
            if (apptRepo.count() == 0) {
                Appointment ap1 = new Appointment();
                ap1.setAppointmentNumber("APT-2026-1001");
                ap1.setPatientName("Vedant Tidke");
                ap1.setPatientPhone("+1 (555) 234-5678");
                ap1.setDoctorName("Dr. Crick");
                ap1.setDoctorSpecialty("Cardiologist");
                ap1.setDoctorAvatar("https://images.unsplash.com/photo-1622253692010-333f2da6031d?w=300&auto=format&fit=crop&q=80");
                ap1.setDate("2026-11-12");
                ap1.setTimeSlot("10:00 AM");
                ap1.setType("ONLINE");
                ap1.setStatus("UPCOMING");
                ap1.setFee(25.0);
                ap1.setPaymentMethod("Credit Card (Visa ending in 4242)");
                ap1.setSymptoms("Chest tightness during brisk walking, routine follow-up");

                Appointment ap2 = new Appointment();
                ap2.setAppointmentNumber("APT-2026-1002");
                ap2.setPatientName("Vedant Tidke");
                ap2.setPatientPhone("+1 (555) 234-5678");
                ap2.setDoctorName("Dr. Strain");
                ap2.setDoctorSpecialty("General Physician");
                ap2.setDoctorAvatar("https://images.unsplash.com/photo-1612349317150-e413f6a5b16d?w=300&auto=format&fit=crop&q=80");
                ap2.setDate("2026-11-02");
                ap2.setTimeSlot("02:30 PM");
                ap2.setType("PHYSICAL");
                ap2.setStatus("COMPLETED");
                ap2.setFee(22.0);
                ap2.setPaymentMethod("Apple Pay");
                ap2.setSymptoms("Seasonal viral fever and throat irritation");

                apptRepo.saveAll(List.of(ap1, ap2));

                // Seed Prescription for completed appointment
                Prescription rx = new Prescription();
                rx.setAppointmentId(ap2.getId());
                rx.setDoctorName(ap2.getDoctorName());
                rx.setPatientName(ap2.getPatientName());
                rx.setDiagnosis("Acute Upper Respiratory Tract Infection (Resolved)");
                rx.setMedicationsJson("[{\"name\":\"Amoxicillin 500mg\",\"dosage\":\"1 capsule 3x daily\",\"duration\":\"7 Days\"},{\"name\":\"Paracetamol 650mg\",\"dosage\":\"1 tablet PRN\",\"duration\":\"3 Days\"}]");
                rx.setClinicalNotes("Patient presented with low-grade fever and cough. Vitals normal. Keep hydrated and rest.");
                rxRepo.save(rx);
            }
        };
    }
}
