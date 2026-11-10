package com.instantdoctor.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "wallet_transactions")
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    private Long userId;
    private Double amount;
    private String type; // CREDIT, DEBIT
    private String description;
    private String specialty;
    private String referenceType; // CONSULTATION, DEPOSIT, WITHDRAWAL, REFUND
    private LocalDateTime createdAt = LocalDateTime.now();

    public WalletTransaction() {}

    public WalletTransaction(Long id, Long userId, Double amount, String type, String description, String specialty, String referenceType) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.specialty = specialty;
        this.referenceType = referenceType;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Wallet getWallet() { return wallet; }
    public void setWallet(Wallet wallet) { this.wallet = wallet; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }

    public String getReferenceType() { return referenceType; }
    public void setReferenceType(String referenceType) { this.referenceType = referenceType; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
