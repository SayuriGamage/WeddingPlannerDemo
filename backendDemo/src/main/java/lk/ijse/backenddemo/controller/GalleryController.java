package lk.ijse.backenddemo.controller;


import lk.ijse.backenddemo.dto.GalleryDTO;
import lk.ijse.backenddemo.dto.ServiceDTO;
import lk.ijse.backenddemo.entity.Gallery;
import lk.ijse.backenddemo.entity.Services;
import lk.ijse.backenddemo.repo.GalleryRepository;
import lk.ijse.backenddemo.repo.ServiceRepository;
import lk.ijse.backenddemo.service.GalleryService;
import lk.ijse.backenddemo.service.ServiceService;
import lk.ijse.backenddemo.util.PicEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/gallery")
@CrossOrigin
public class GalleryController {

    @Autowired
    private GalleryService galleryService;

    @Autowired
   private ServiceRepository serviceRepository;

    @Autowired
  private   GalleryRepository galleryRepository;

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAuthority('vendor')")
    public ResponseEntity<GalleryDTO> saveGallery(
            @RequestPart("serviceId") String serviceId,
            @RequestPart("image") MultipartFile image
    ){
        GalleryDTO galleryDTO = new GalleryDTO();
        String images= PicEncoder.generatePicture(image);
        galleryDTO.setImage(images);
        Optional<Services> servicesOptional= galleryRepository.findServiceById(UUID.fromString(serviceId));
        Services services=servicesOptional.get();
        GalleryDTO savedGallery = galleryService.saveGallery(galleryDTO,services);
        return ResponseEntity.ok(savedGallery);
    }

    @GetMapping("/all/{serviceId}")
    public ResponseEntity<?> getAllGalleryImages(@PathVariable String serviceId) {
          Optional<Services> services=serviceRepository.findService(UUID.fromString(serviceId));
           List<Gallery> galleries=galleryService.getAllGalerry(services);

           return ResponseEntity.ok(galleries);

    }

}
