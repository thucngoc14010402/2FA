package com.example.demo.service;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import org.springframework.stereotype.Service;

@Service
public class TwoFactorAuthService {
    private final GoogleAuthenticator gAuth;

    public TwoFactorAuthService() {
        this.gAuth = new GoogleAuthenticator();
    }

    // Tạo khóa bí mật và mã QR
    public String[] generate2FAKey(String username, String issuer) {
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        String qrCodeUrl = GoogleAuthenticatorQRGenerator.getOtpAuthURL(issuer, username, key);
        return new String[]{key.getKey(), qrCodeUrl};
    }

    // Xác thực mã 2FA
    public boolean verifyCode(String secretKey, int code) {
        return gAuth.authorize(secretKey, code);
    }
}