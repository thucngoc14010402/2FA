package com.example.demo.service;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TwoFactorAuthService {
    private static final Logger logger = LoggerFactory.getLogger(TwoFactorAuthService.class);
    private final GoogleAuthenticator gAuth;

    public TwoFactorAuthService() {
        this.gAuth = new GoogleAuthenticator();
    }

    public String[] generate2FAKey(String username, String issuer) {
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        String secretKey = key.getKey();
        String qrCodeUrl = GoogleAuthenticatorQRGenerator.getOtpAuthURL(issuer, username, key);
        logger.info("Generated secretKey for username {}: {}", username, secretKey);
        return new String[]{secretKey, qrCodeUrl};
    }

    public boolean verifyCode(String secretKey, int code) {
        logger.info("Verifying code {} with secretKey: {}", code, secretKey);
        boolean isValid = gAuth.authorize(secretKey, code);
        logger.info("Verification result for code {} with secretKey {}: {}", code, secretKey, isValid);
        return isValid;
    }
}