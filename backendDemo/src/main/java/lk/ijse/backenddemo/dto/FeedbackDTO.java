package lk.ijse.backenddemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {
    private UUID id;
    private String title;
    private String name;
    private String email;
    private String review;
    private int rating;
    private LocalDateTime submittedAt;
    private String serviceId;
}
