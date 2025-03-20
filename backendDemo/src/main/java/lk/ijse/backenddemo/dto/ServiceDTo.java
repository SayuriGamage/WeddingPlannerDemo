package lk.ijse.backenddemo.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class ServiceDTo {
    private Long id;
    private Long vendorId; // This will hold the vendor's ID
    private String serviceName;
    private String description;
    private BigDecimal price;
    private String availability;
    private Timestamp createdAt;
}
