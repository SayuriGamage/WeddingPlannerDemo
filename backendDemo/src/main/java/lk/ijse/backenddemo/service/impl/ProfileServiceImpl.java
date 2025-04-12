package lk.ijse.backenddemo.service.impl;

import lk.ijse.backenddemo.dto.UserDTO;
import lk.ijse.backenddemo.entity.Category;
import lk.ijse.backenddemo.entity.User;
import lk.ijse.backenddemo.repo.ProfileRepository;
import lk.ijse.backenddemo.service.ProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ProfileServiceImpl implements ProfileService {

   @Autowired
   private ModelMapper modelMapper;
    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public User getUserProfile(String email) {
        Optional<User> userOptional = profileRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new RuntimeException("User not found with email: " + email);
        }
    }
    @Override
    public Object updateProfile(String currentEmail, UserDTO userDTO) {
        Optional<User> optionalUser = profileRepository.findByEmail(currentEmail);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found with email: " + currentEmail);
        }

        User user = optionalUser.get();

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setContact(userDTO.getContact());
        user.setPhoto(userDTO.getPhoto());


        User updatedUser = profileRepository.save(user);


        return modelMapper.map(updatedUser, UserDTO.class);

    }

    @Override
    public List<User> getUserDetails(UUID uid) {
        Optional<User> userOptional = profileRepository.findById(uid);
        if (userOptional.isPresent()) {
            return List.of(userOptional.get());
        } else {
            throw new RuntimeException("User not found with ID: " + uid);
        }
    }

}
