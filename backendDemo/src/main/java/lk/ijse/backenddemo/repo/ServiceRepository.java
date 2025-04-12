package lk.ijse.backenddemo.repo;


import lk.ijse.backenddemo.entity.Services;
import lk.ijse.backenddemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ServiceRepository extends JpaRepository<Services, UUID> {



    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findUserByEmail(@Param("email") String emails);

    @Query("SELECT s FROM Services s WHERE s.user = :user")
    List<Services> findByUser(@Param("user") User user);

    @Query("SELECT s FROM Services s WHERE s.category = :category")
    List<Services> findByCategory(@Param("category") String category);

    @Query("SELECT s FROM Services s WHERE s.id = :serviceId")
    Optional<Services> findService(@Param("serviceId") UUID serviceId);

    @Query("SELECT s FROM Services s WHERE s.id = :uuid")
    Optional<Services> findServiceByServiceId(@Param("uuid") UUID uuid);


}
