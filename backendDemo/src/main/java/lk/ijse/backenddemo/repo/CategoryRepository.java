package lk.ijse.backenddemo.repo;

import jakarta.transaction.Transactional;
import lk.ijse.backenddemo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {


    boolean existsByName(String name);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Category WHERE name = ?1", nativeQuery = true)
    void deleteByName(String name);


}