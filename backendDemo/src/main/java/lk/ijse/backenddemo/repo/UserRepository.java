package lk.ijse.backenddemo.repo;


import lk.ijse.backenddemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,String> {

    User findByEmail(String userName);

    boolean existsByEmail(String userName);


    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmails(@Param("email") String email);


}