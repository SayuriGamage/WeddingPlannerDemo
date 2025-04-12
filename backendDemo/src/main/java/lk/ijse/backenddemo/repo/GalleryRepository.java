package lk.ijse.backenddemo.repo;


import lk.ijse.backenddemo.dto.ServiceDTO;
import lk.ijse.backenddemo.entity.Gallery;
import lk.ijse.backenddemo.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, UUID> {

     @Query("SELECT s FROM Services s WHERE s.id = :serviceId")
    Optional<Services> findServiceById(@Param("serviceId") UUID serviceId);  // Change to UUID


    @Query("SELECT g FROM Gallery g WHERE g.services = :services")
    List<Gallery> findGallery(@Param("services") Services services);


}
