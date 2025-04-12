package lk.ijse.backenddemo.service;

import lk.ijse.backenddemo.dto.GalleryDTO;
import lk.ijse.backenddemo.dto.ServiceDTO;
import lk.ijse.backenddemo.entity.Gallery;
import lk.ijse.backenddemo.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GalleryService  {

    GalleryDTO saveGallery(GalleryDTO galleryDTO, Services services);


    List<Gallery> getAllGalerry(Optional<Services> services);

}
