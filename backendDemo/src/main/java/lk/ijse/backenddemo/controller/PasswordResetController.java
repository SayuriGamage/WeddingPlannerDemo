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
        Optional<User> userOptional = Optional.ofNullable(userRepo.findByEmail(email));
        System.out.println(userOptional.get().getName());
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Email not found");
        }

        String otp = String.valueOf((int) (Math.random() * 900000) + 100000);
        PasswordResetToken resetToken = new PasswordResetToken(null, otp, email, LocalDateTime.now().plusMinutes(15));
        tokenRepo.save(resetToken);

        // String resetLink = "http://localhost:3000/reset-password?token=" + token;
        emailService.sendEmailToVendor(email, "Reset your password", "Click here to reset your password: " + otp);

        return ResponseEntity.ok("Reset link sent to email");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        // OTP token table eken email ekata adala token eka ganna
        PasswordResetToken resetToken = tokenRepo.findByEmailAndToken(email, otp);

        if (resetToken == null) {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }

        if (resetToken.getExpiration().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("OTP expired");
        }

        return ResponseEntity.ok("OTP verified successfully");
    }


    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email, @RequestParam String newPassword) {
        // Find the latest token for the given email
        PasswordResetToken resetToken = tokenRepo.findTopByEmailOrderByExpirationDesc(email);

        if (resetToken == null || resetToken.getExpiration().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }

        Optional<User> userOptional = userRepo.findByEmails(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);

        tokenRepo.delete(resetToken); // Invalidate used token

        return ResponseEntity.ok("Password reset successful");
    }

}