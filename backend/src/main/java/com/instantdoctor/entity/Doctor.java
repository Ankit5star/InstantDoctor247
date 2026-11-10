package com.instantdoctor.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String name;
    private String specialty;
    private Double rating = 5.0;
    private Integer reviewsCount = 0;
    private Double hourlyRate = 25.0;
    private Integer experienceYears = 5;
    private Integer patientsCount = 100;

    @Column(length = 1500)
    private String about;
    private String avatarUrl;
    private String location;
    private String clinicName;
    private String clinicAddress;
    private Boolean isAvailableInstant = true;

    private String kycStatus = "VERIFIED"; // PENDING, VERIFIED, REJECTED
    private String nicFrontUrl;
    private String nicBackUrl;
    private String degreeUrl;

    public Doctor() {}

    public Doctor(Long id, String name, String specialty, Double rating, Integer reviewsCount,
                  Double hourlyRate, Integer experienceYears, Integer patientsCount,
                  String about, String avatarUrl, String location, Boolean isAvailableInstant) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.rating = rating;
        this.reviewsCount = reviewsCount;
        this.hourlyRate = hourlyRate;
        this.experienceYears = experienceYears;
        this.patientsCount = patientsCount;
        this.about = about;
        this.avatarUrl = avatarUrl;
        this.location = location;
        this.isAvailableInstant = isAvailableInstant;
        this.kycStatus = "VERIFIED";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public Integer getReviewsCount() { return reviewsCount; }
    public void setReviewsCount(Integer reviewsCount) { this.reviewsCount = reviewsCount; }

    public Double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(Double hourlyRate) { this.hourlyRate = hourlyRate; }

    public Integer getExperienceYears() { return experienceYears; }
    public void setExperienceYears(Integer experienceYears) { this.experienceYears = experienceYears; }

    public Integer getPatientsCount() { return patientsCount; }
    public void setPatientsCount(Integer patientsCount) { this.patientsCount = patientsCount; }

    public String getAbout() { return about; }
    public void setAbout(String about) { this.about = about; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getClinicName() { return clinicName; }
    public void setClinicName(String clinicName) { this.clinicName = clinicName; }

    public String getClinicAddress() { return clinicAddress; }
    public void setClinicAddress(String clinicAddress) { this.clinicAddress = clinicAddress; }

    public Boolean getIsAvailableInstant() { return isAvailableInstant; }
    public void setIsAvailableInstant(Boolean isAvailableInstant) { this.isAvailableInstant = isAvailableInstant; }

    public String getKycStatus() { return kycStatus; }
    public void setKycStatus(String kycStatus) { this.kycStatus = kycStatus; }

    public String getNicFrontUrl() { return nicFrontUrl; }
    public void setNicFrontUrl(String nicFrontUrl) { this.nicFrontUrl = nicFrontUrl; }

    public String getNicBackUrl() { return nicBackUrl; }
    public void setNicBackUrl(String nicBackUrl) { this.nicBackUrl = nicBackUrl; }

    public String getDegreeUrl() { return degreeUrl; }
    public void setDegreeUrl(String degreeUrl) { this.degreeUrl = degreeUrl; }
}
