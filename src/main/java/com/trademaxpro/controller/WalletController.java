package com.trademaxpro.controller;

import com.trademaxpro.service.WalletService;
import org.springframework.web.bind.annotation.*;

// wallet apis: add, withdraw, view
@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/add")
    public double addMoney(@RequestParam String userId,
                           @RequestParam double amount) {
        return walletService.addMoney(userId, amount);
    }

    @PostMapping("/withdraw")
    public double withdrawMoney(@RequestParam String userId,
                                @RequestParam double amount) {
        return walletService.withdrawMoney(userId, amount);
    }

    @GetMapping("/{userId}")
    public double viewBalance(@PathVariable String userId) {
        return walletService.viewBalance(userId);
    }
}
