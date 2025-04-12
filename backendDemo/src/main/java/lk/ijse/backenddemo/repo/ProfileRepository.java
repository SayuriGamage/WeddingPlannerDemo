package lk.ijse.backenddemo.repo;


import lk.ijse.backenddemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileRepository  extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> findByEmail(String email);

        @Query("SELECT s.user FROM Services s WHERE s.id = :serviceId")
        Optional<User> getUserById(@Param("serviceId") UUID serviceId);
    }


