package com.instantdoctor.repository;

import com.instantdoctor.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByStatusIgnoreCase(String status);
    List<Appointment> findAllByOrderByCreatedAtDesc();
    List<Appointment> findByPatientEmailOrderByCreatedAtDesc(String patientEmail);
    List<Appointment> findByPatientEmailAndStatusIgnoreCaseOrderByCreatedAtDesc(String patientEmail, String status);
    List<Appointment> findByDoctorIdOrderByCreatedAtDesc(Long doctorId);
}
