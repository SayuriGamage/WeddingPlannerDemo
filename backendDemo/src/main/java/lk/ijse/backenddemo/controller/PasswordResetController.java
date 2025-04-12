package lk.ijse.backenddemo.controller;

import lk.ijse.backenddemo.entity.PasswordResetToken;
import lk.ijse.backenddemo.entity.User;
import lk.ijse.backenddemo.repo.PasswordResetTokenRepo;
import lk.ijse.backenddemo.repo.UserRepository;
import lk.ijse.backenddemo.repo.UserRepository;
import lk.ijse.backenddemo.service.impl.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
@RequiredArgsConstructor
public class PasswordResetController {

    private final UserRepository userRepo;
    private final PasswordResetTokenRepo tokenRepo;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            // For security, don't reveal if email exists
            return ResponseEntity.ok("If this email exists, an OTP has been sent");
        }

        // Delete any existing tokens for this email
        tokenRepo.deleteByEmail(email);

        // Generate 6-digit OTP
        String otp = String.format("%06d", new Random().nextInt(999999));

        PasswordResetToken resetToken = new PasswordResetToken(
                null,
                otp,
                email,
                LocalDateTime.now().plusMinutes(15)
        );
        tokenRepo.save(resetToken);

      
        String emailContent = "Your password reset OTP is: " + otp + "\n"
                + "This OTP is valid for 15 minutes.";

        emailService.sendEmailToVendor(
                email,
                "Password Reset OTP",
                emailContent
        );

        return ResponseEntity.ok("OTP sent to email");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(
            @RequestParam String email,
            @RequestParam String otp
    ) {
        PasswordResetToken resetToken = tokenRepo.findByTokenAndEmail(otp, email);

        if (resetToken == null) {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }

        if (resetToken.getExpiration().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("OTP has expired");
        }

        return ResponseEntity.ok("OTP verified successfully");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam String token,
            @RequestParam String newPassword
    ) {
        PasswordResetToken resetToken = tokenRepo.findByToken(token);

        if (resetToken == null || resetToken.getExpiration().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Invalid or expired OTP");
        }

        User user = userRepo.findByEmail(resetToken.getEmail());
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        // Check if new password is different
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            return ResponseEntity.badRequest().body("New password must be different");
        }

        // Update password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);

        // Clean up token
        tokenRepo.delete(resetToken);

        return ResponseEntity.ok("Password reset successful");
    }
}
