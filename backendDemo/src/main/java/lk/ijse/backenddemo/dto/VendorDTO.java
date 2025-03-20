package lk.ijse.backenddemo.dto;

import lombok.Data;

@Data
public class VendorDTO {
    private Long id;
    private String businessName;
    private String businessDescription;
    private String category;
    private String location;
    private String availability;
    private String coverPhoto;
    private Float rating;
    private String status;
}
