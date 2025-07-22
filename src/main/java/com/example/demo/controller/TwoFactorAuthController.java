package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.TwoFactorAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/2fa")
public class TwoFactorAuthController {
    private static final Logger logger = LoggerFactory.getLogger(TwoFactorAuthController.class);
    @Autowired
    private TwoFactorAuthService twoFactorAuthService;

    @PostMapping("/generate")
    public ResponseEntity<?> generate2FA(@RequestBody User user) {
        logger.info("Generating 2FA for username: {}", user.getUsername());
        String[] result = twoFactorAuthService.generate2FAKey(user.getUsername(), "YourAppName");
        return ResponseEntity.ok(new String[]{result[0], result[1]});
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify2FA(@RequestParam String secretKey, @RequestParam int code) {
        logger.info("Verifying 2FA with secretKey: {} and code: {}", secretKey, code);
        boolean isValid = twoFactorAuthService.verifyCode(secretKey, code);
        return ResponseEntity.ok(isValid ? "Mã 2FA hợp lệ" : "Mã 2FA không hợp lệ");
    }
}