package pl.tumanski.discounthub.categorymanagement.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;
import pl.tumanski.discounthub.postmanagement.model.entity.Post;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Entity
@Table(
        name = "category",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_name", columnNames = {"name", "is_active"}),
                @UniqueConstraint(name = "unique_url", columnNames = {"url", "is_active"}),
                @UniqueConstraint(name = "unique_icon", columnNames = {"icon", "is_active"})
        }
)
@SQLDelete(sql = "UPDATE category SET is_active = false WHERE id=?")
@SQLRestriction("is_active = true")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "icon", nullable = false)
    private String icon;

    @Column(name = "is_active", columnDefinition = "boolean default true", nullable = false)
    private boolean isActive = true;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private OffsetDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "last_updated_date")
    private OffsetDateTime lastUpdatedDate;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Post> posts;
}
