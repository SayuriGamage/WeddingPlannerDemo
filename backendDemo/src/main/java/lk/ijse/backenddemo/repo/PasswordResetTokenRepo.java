package lk.ijse.backenddemo.repo;

import lk.ijse.backenddemo.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
    PasswordResetToken findByTokenAndEmail(String token, String email);
    void deleteByEmail(String email);

    PasswordResetToken findByEmailAndToken(String email, String otp);


    @Query("SELECT t FROM PasswordResetToken t WHERE t.email = :email")
    PasswordResetToken findTopByEmailOrderByExpirationDesc(@Param("email") String email);

}
