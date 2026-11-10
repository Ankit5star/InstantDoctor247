package com.instantdoctor.controller;

import com.instantdoctor.dto.ApiResponse;
import com.instantdoctor.entity.Wallet;
import com.instantdoctor.entity.WalletTransaction;
import com.instantdoctor.repository.WalletRepository;
import com.instantdoctor.repository.WalletTransactionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletRepository walletRepository;
    private final WalletTransactionRepository transactionRepository;

    public WalletController(WalletRepository walletRepository, WalletTransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getWallet(@PathVariable Long userId) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseGet(() -> walletRepository.save(new Wallet(null, userId, 450.00)));

        List<WalletTransaction> transactions = transactionRepository.findByUserIdOrderByCreatedAtDesc(userId);

        Map<String, Object> data = new HashMap<>();
        data.put("wallet", wallet);
        data.put("transactions", transactions);

        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestParam Long userId, @RequestParam Double amount, @RequestParam String method) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseGet(() -> walletRepository.save(new Wallet(null, userId, 0.0)));

        wallet.setBalance(wallet.getBalance() + amount);
        wallet.setUpdatedAt(LocalDateTime.now());
        walletRepository.save(wallet);

        WalletTransaction tx = new WalletTransaction(
                null, userId, amount, "CREDIT", "Wallet Top-up via " + method, method, "DEPOSIT"
        );
        transactionRepository.save(tx);

        return ResponseEntity.ok(ApiResponse.ok("Funds added successfully", wallet));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestParam Long userId, @RequestParam Double amount) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (wallet.getBalance() < amount) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Insufficient wallet balance"));
        }

        wallet.setBalance(wallet.getBalance() - amount);
        wallet.setUpdatedAt(LocalDateTime.now());
        walletRepository.save(wallet);

        WalletTransaction tx = new WalletTransaction(
                null, userId, amount, "DEBIT", "Bank Payout Transfer", "ACH Payout", "WITHDRAWAL"
        );
        transactionRepository.save(tx);

        return ResponseEntity.ok(ApiResponse.ok("Payout initiated successfully", wallet));
    }
}
