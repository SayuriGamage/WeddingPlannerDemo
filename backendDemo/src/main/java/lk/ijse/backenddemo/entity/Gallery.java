package lk.ijse.backenddemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Gallery {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID gid;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "service_id", nullable = false)
    private Services services;

    @Column(columnDefinition = "LONGTEXT")
    private String image;
}
