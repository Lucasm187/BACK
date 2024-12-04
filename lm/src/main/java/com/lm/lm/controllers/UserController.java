package com.lm.lm.controllers;

import com.lm.lm.models.Purchase;
import com.lm.lm.models.User;
import com.lm.lm.repositories.PurchaseRepository;
import com.lm.lm.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private PurchaseRepository purchaseRepository;
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userRepository.save(user));
    }

        @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            return ResponseEntity.ok(existingUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }

    @GetMapping("/{userId}/purchases")
    public ResponseEntity<List<Purchase>> getUserPurchases(@PathVariable Long userId) {
        List<Purchase> purchases = purchaseRepository.findByUserId(userId);
        return ResponseEntity.ok(purchases);
    }
}
