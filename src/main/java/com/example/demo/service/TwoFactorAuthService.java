package com.example.demo.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.EncodeHintType;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        // Tạo URL otpauth:// trực tiếp
        String otpAuthUrl = String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s&algorithm=SHA1&digits=6&period=30",
                issuer, username, secretKey, issuer);
        logger.info("Generated secretKey for username {}: {}", username, secretKey);
        logger.info("Generated OTP Auth URL: {}", otpAuthUrl);
        return new String[]{secretKey, otpAuthUrl}; // Trả về otpAuthUrl thay vì URL từ QRGenerator
    }

    public boolean verifyCode(String secretKey, int code) {
        logger.info("Verifying code {} with secretKey: {}", code, secretKey);
        boolean isValid = gAuth.authorize(secretKey, code);
        logger.info("Verification result for code {} with secretKey {}: {}", code, secretKey, isValid);
        return isValid;
    }

    public byte[] generateQRCodeImage(String username, String issuer) throws WriterException, IOException {
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        String secretKey = key.getKey();
        // Tạo chuỗi otpauth:// trực tiếp
        String otpAuthUrl = String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s&algorithm=SHA1&digits=6&period=30",
                issuer, username, secretKey, issuer);
        logger.info("Generated OTP Auth URL: {}", otpAuthUrl);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Integer> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 2); // Thêm margin
        BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthUrl, BarcodeFormat.QR_CODE, 500, 500, hints);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }
}