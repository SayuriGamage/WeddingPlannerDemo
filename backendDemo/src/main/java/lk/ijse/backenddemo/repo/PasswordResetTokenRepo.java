package lk.ijse.backenddemo.repo;

import lk.ijse.backenddemo.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
    PasswordResetToken findByTokenAndEmail(String token, String email);
    void deleteByEmail(String email);
}
