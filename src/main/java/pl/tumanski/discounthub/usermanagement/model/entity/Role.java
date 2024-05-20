package pl.tumanski.discounthub.usermanagement.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "roles")
@SQLDelete(sql = "UPDATE roles SET is_active = false WHERE id=?")
@SQLRestriction("is_active = true")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "is_active", columnDefinition = "boolean default true", nullable = false)
    private boolean isActive;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private OffsetDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "last_updated_date")
    private OffsetDateTime lastUpdatedDate;
}
