package lk.ijse.backenddemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID fid;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "service_id", nullable = false)
    private Services services;
    private String name;
    @Column(columnDefinition = "LONGTEXT")
    private String logo;
}
