package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.TwoFactorAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/2fa")
public class TwoFactorAuthController {
    @Autowired
    private TwoFactorAuthService twoFactorAuthService;

    // API để tạo mã QR
    @PostMapping("/generate")
    public ResponseEntity<?> generate2FA(@RequestBody User user) {
        String[] result = twoFactorAuthService.generate2FAKey(user.getUsername(), "YourAppName");
        return ResponseEntity.ok(new String[]{result[0], result[1]});
    }

    // API để xác thực mã 2FA
    @PostMapping("/verify")
    public ResponseEntity<?> verify2FA(@RequestParam String secretKey, @RequestParam int code) {
        boolean isValid = twoFactorAuthService.verifyCode(secretKey, code);
        return ResponseEntity.ok(isValid ? "Mã 2FA hợp lệ" : "Mã 2FA không hợp lệ");
    }
}