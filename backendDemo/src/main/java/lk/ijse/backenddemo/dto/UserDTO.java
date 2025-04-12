package lk.ijse.backenddemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private String email;
    private String password;
    private String name;
    private String role;
    private String address;
    private String contact;
    private String photo;

    public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
