package lk.ijse.backenddemo.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

   @Id
    @GeneratedValue(strategy = GenerationType.UUID)
   @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;
    private String status;
    private double amount;
    private LocalDateTime paidAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
