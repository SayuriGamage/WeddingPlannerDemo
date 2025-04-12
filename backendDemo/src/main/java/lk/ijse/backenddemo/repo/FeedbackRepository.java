package lk.ijse.backenddemo.repo;


import lk.ijse.backenddemo.entity.Feedback;
import lk.ijse.backenddemo.entity.Gallery;
import lk.ijse.backenddemo.entity.Services;
import lk.ijse.backenddemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FeedbackRepository  extends JpaRepository<Feedback, UUID> {

    @Query("SELECT s FROM Services s WHERE s.id = :serviceId")
    Optional<Services> findByServiceId(@Param("serviceId") UUID serviceId);


    @Query("SELECT f FROM Feedback f WHERE f.services= :services")
    List<Feedback> findFeedbacksByServiceId(@Param("services") Services services);


    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

}
