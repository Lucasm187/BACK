package com.lm.lm.controllers;

import com.lm.lm.models.Purchase;
import com.lm.lm.models.User;
import com.lm.lm.repositories.PurchaseRepository;
import com.lm.lm.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{userId}")
    public ResponseEntity<Purchase> createPurchase(@PathVariable Long userId, @RequestBody Purchase purchase) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        purchase.setUser(user);
        Purchase savedPurchase = purchaseRepository.save(purchase);
        return ResponseEntity.ok(savedPurchase);
    }
}
