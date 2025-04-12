package lk.ijse.backenddemo.controller;


import lk.ijse.backenddemo.dto.FeatureDTO;
import lk.ijse.backenddemo.entity.Services;
import lk.ijse.backenddemo.repo.FeatureRepository;
import lk.ijse.backenddemo.service.FeatureService;
import lk.ijse.backenddemo.util.PicEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/features")
@CrossOrigin
public class FeatureController {


    @Autowired
    private FeatureService featureService;

    @Autowired
    private FeatureRepository featureRepository;

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAuthority('vendor')")
    public ResponseEntity<FeatureDTO> saveFeature(
            @RequestPart("serviceId") String serviceId,
            @RequestPart("name") String name,
            @RequestPart("logo")MultipartFile logo
            ){
        String logos= PicEncoder.generatePicture(logo);
        FeatureDTO featureDTO = new FeatureDTO();
        System.out.println("servicesId = " + serviceId);
    Optional<Services> servicesOptional= featureRepository.findServiceById(UUID.fromString(serviceId));
    Services services=servicesOptional.get();
        featureDTO.setName(name);
        featureDTO.setLogo(logos);
        FeatureDTO savedFeature = featureService.saveFeature(featureDTO,services);
        return ResponseEntity.ok(savedFeature);
    }
}
