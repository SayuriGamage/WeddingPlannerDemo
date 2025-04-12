package lk.ijse.backenddemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private User user;
    private String category;
    private String title;
    @Column(length = 5000)
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
    @Column(columnDefinition = "LONGTEXT")
    private String image;
    @Column(columnDefinition = "LONGTEXT")
    private String logo;

    @OneToMany(mappedBy = "services",cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Feature> feature;

    @OneToMany(mappedBy = "services",cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Gallery> gallery;


    @OneToMany(mappedBy = "services", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Feedback> feedbacks;


}