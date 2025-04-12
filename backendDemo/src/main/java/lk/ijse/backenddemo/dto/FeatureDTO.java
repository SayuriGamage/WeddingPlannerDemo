package lk.ijse.backenddemo.dto;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class FeatureDTO {

    private String serviceId;
    private String name;
    private String logo;

}
