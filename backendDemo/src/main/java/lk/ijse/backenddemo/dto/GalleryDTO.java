package lk.ijse.backenddemo.dto;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data

public class GalleryDTO {
    private String serviceId;
    private String image;
}
