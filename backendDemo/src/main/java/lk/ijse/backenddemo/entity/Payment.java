import jakarta.persistence.*;
import lk.ijse.backenddemo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

   @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String orderId;
    private String status;
    private double amount;
    private String currency;
    private LocalDateTime paidAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
