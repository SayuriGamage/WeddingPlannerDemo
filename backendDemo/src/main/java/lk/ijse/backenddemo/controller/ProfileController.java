package lk.ijse.backenddemo.controller;


import lk.ijse.backenddemo.dto.UserDTO;
import lk.ijse.backenddemo.entity.User;
import lk.ijse.backenddemo.repo.ProfileRepository;
import lk.ijse.backenddemo.service.ProfileService;
import lk.ijse.backenddemo.util.PicEncoder;
import org.modelmapper.ModelMapper;
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
@RequestMapping("/api/v1/profile")
@CrossOrigin
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProfileRepository profileRepository;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInEmail = authentication.getName();
        System.out.println("✔ Logged-in user email: " + loggedInEmail);

        User user = profileService.getUserProfile(loggedInEmail);
        return ResponseEntity.ok(user);
    }

    @PutMapping(value = "/update/{currentEmail}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<?> updateProfile(
            @PathVariable("currentEmail") String currentEmail,
            @RequestPart("name") String name,
            @RequestPart("contact") String contact,
            @RequestPart("email") String email,
            @RequestPart("photo")MultipartFile photo
            ){
        UserDTO userDTO=new UserDTO();
        userDTO.setName(name);
        userDTO.setContact(contact);
        userDTO.setEmail(email);
        userDTO.setPhoto(PicEncoder.generatePicture(photo));

        return ResponseEntity.ok(profileService.updateProfile(currentEmail,userDTO));
    }

    @GetMapping(value = "/byId/{serviceId}")
   public ResponseEntity<?> getVendorProfile(@PathVariable String serviceId){
        System.out.println("menna service id eka"+serviceId);

        Optional<User> user= profileRepository.getUserById(UUID.fromString(serviceId));
        System.out.println("userge id eka enawa"+ user.get().getUid());
        List<User> userlist=profileService.getUserDetails(user.get().getUid());
        return ResponseEntity.ok(userlist);
    }


}


