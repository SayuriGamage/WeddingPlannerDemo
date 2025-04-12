package lk.ijse.backenddemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDTO {
    private String userId;
    private String category;
    private String title;
    private String description;
    private String tagline;
    private String address;
    private String mapAddress;
    private String basePrice;
    private String email;
    private String website;
    private String phone;
    private String facebook;
    private String twitter;
    private String google;
    private String instagram;

    private String image;
    private String logo;
}