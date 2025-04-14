package lk.ijse.backenddemo.controller;


import lk.ijse.backenddemo.dto.ServiceDTO;
import lk.ijse.backenddemo.entity.Category;
import lk.ijse.backenddemo.entity.Services;
import lk.ijse.backenddemo.entity.User;
import lk.ijse.backenddemo.repo.ProfileRepository;
import lk.ijse.backenddemo.repo.ServiceRepository;
import lk.ijse.backenddemo.service.ServiceService;
import lk.ijse.backenddemo.util.PicEncoder;
import lk.ijse.backenddemo.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/services")
@CrossOrigin
public class  ServiceController {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ProfileRepository profileRepository;


    @PostMapping(value = "/save",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('vendor')")
    public ResponseEntity<ServiceDTO> saveService(
            @RequestPart("title") String title,
            @RequestPart("description") String description,
            @RequestPart("category") String category,
            @RequestPart("tagline") String tagline,
            @RequestPart("basePrice") String basePrice,
            @RequestPart("address") String address,
            @RequestPart("mapAddress") String mapAddress,
            @RequestPart("email") String email,
            @RequestPart("website") String website,
            @RequestPart("phone") String phone,
            @RequestPart("facebook") String facebook,
            @RequestPart("twitter") String twitter,
            @RequestPart("google") String google,
            @RequestPart("instagram") String instagram,
            @RequestPart("image") MultipartFile image,
            @RequestPart("logo") MultipartFile logo
    ) {

        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setTitle(title);
        serviceDTO.setDescription(description);
        serviceDTO.setCategory(category);
        serviceDTO.setTagline(tagline);
        serviceDTO.setBasePrice(basePrice);
        serviceDTO.setAddress(address);
        serviceDTO.setMapAddress(mapAddress);
        serviceDTO.setEmail(email);
        serviceDTO.setWebsite(website);
        serviceDTO.setPhone(phone);
        serviceDTO.setFacebook(facebook);
        serviceDTO.setTwitter(twitter);
        serviceDTO.setGoogle(google);
        serviceDTO.setInstagram(instagram);
        serviceDTO.setImage(PicEncoder.generatePicture(image));
        serviceDTO.setLogo(PicEncoder.generatePicture(logo));


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInEmail = authentication.getName();

       // System.out.println("loggedInEmail = " + loggedInEmail);
        Optional<User> userOptional = serviceRepository.findUserByEmail(loggedInEmail);
        User user = userOptional.get();
        ServiceDTO savedService = serviceService.saveService(serviceDTO,user);

        return ResponseEntity.ok(savedService);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Services>> getServicesForLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInEmail = authentication.getName();
        Optional<User> user = profileRepository.findByEmail(loggedInEmail);
        System.out.println(user.get().getUid());
        String userId = String.valueOf(user.get().getUid());

        List<Services> services = serviceService.getServicesByUserId(user);
        return ResponseEntity.ok(services);
    }

    @GetMapping("/search/{id}")
    @PreAuthorize("hasAuthority('vendor')")
    public ResponseEntity<ServiceDTO> getServiceById(@PathVariable String id) {
        return ResponseEntity.ok(serviceService.getServiceById(id));
    }

    @DeleteMapping(path = "/delete/{id}")
    @PreAuthorize("hasAuthority('vendor')")
    public ResponseEntity<ResponseUtil> deleteCategory(@PathVariable String id) {
            serviceService.deleteCategory(id);
            return ResponseEntity.ok(new ResponseUtil(200, "Category has been deleted", null));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Services>> getServicesByCategory(@PathVariable String category) {
        System.out.println("methanata wenakn enawa   "+ category);
        List<Services> services = serviceService.getServicesByCategory(category);
        return ResponseEntity.ok(services);
    }

    @GetMapping("/load/{serviceId}")
    public ResponseEntity<?> getAllDetailsForService(@PathVariable String serviceId) {
        Optional<Services> services = serviceService.getAllServicesById(serviceId);
        return ResponseEntity.ok(services);
    }


    @GetMapping("/loadall")
    public ResponseEntity<List<Services>> getAllServices() {
        return ResponseEntity.ok(serviceService.getAllServicess());

    }

}
