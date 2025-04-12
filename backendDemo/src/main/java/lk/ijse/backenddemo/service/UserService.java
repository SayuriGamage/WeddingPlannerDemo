package lk.ijse.backenddemo.service;


import lk.ijse.backenddemo.dto.UserDTO;

public interface UserService {
    int saveUser(UserDTO userDTO);
    UserDTO searchUser(String username);
}