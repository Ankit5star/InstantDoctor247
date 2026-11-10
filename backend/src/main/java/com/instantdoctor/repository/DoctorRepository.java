package com.instantdoctor.repository;

import com.instantdoctor.entity.Doctor;
import com.instantdoctor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findBySpecialtyIgnoreCase(String specialty);
    List<Doctor> findByNameContainingIgnoreCase(String name);
    List<Doctor> findByIsAvailableInstantTrue();
    List<Doctor> findByKycStatus(String kycStatus);
    Optional<Doctor> findByUser(User user);
    Optional<Doctor> findByUserId(Long userId);
}
