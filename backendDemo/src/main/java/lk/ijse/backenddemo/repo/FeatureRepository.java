package lk.ijse.backenddemo.repo;


import lk.ijse.backenddemo.entity.Feature;
import lk.ijse.backenddemo.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, UUID> {


    @Query("SELECT s FROM Services s WHERE s.id = :serviceId")
    Optional<Services> findServiceById(@Param("serviceId") UUID serviceId);  // Change to UUID
}

