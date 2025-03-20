package lk.ijse.backenddemo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "vendors")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Vendor {

    @Id
    private Long id;

    private String businessName;

    private String businessDescription;

    private String category;

    private String location;

    private String availability;

    private String coverPhoto;

    private Float rating ;

    private String status;



}
