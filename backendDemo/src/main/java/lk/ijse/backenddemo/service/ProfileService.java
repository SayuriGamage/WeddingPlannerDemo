package lk.ijse.backenddemo.service;

import lk.ijse.backenddemo.dto.UserDTO;
import lk.ijse.backenddemo.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfileService {


    User getUserProfile(String loggedInEmail);

    Object updateProfile(String currentEmail, UserDTO userDTO);


    List<User> getUserDetails(UUID uid);

}
