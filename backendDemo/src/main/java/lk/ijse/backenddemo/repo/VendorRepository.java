package lk.ijse.backenddemo.repo;

import lk.ijse.backenddemo.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    List<Vendor> findByBusinessNameContaining(String businessName);
}
