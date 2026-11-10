package com.instantdoctor.dto;

public class AuthResponse {

    private String token;
    private String tokenType = "Bearer";
    private Long id;
    private String email;
    private String name;
    private String role;
    private String avatarUrl;
    private String phone;
    private String gender;
    private String bloodGroup;

    public AuthResponse() {}

    public AuthResponse(String token, Long id, String email, String name, String role, String avatarUrl) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.avatarUrl = avatarUrl;
    }

    public AuthResponse(String token, Long id, String email, String name, String role, String avatarUrl, String phone, String gender, String bloodGroup) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.avatarUrl = avatarUrl;
        this.phone = phone;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
}
