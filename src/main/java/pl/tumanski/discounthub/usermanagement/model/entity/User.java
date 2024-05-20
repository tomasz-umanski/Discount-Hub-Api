package pl.tumanski.discounthub.usermanagement.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;
import pl.tumanski.discounthub.postmanagement.model.entity.Post;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_email", columnNames = {"email", "is_active"}),
                @UniqueConstraint(name = "unique_username", columnNames = {"user_name", "is_active"})
        }
)
@SQLDelete(sql = "UPDATE users SET is_active = false WHERE id=?")
@SQLRestriction("is_active = true")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    @Column(name = "last_active")
    private OffsetDateTime lastActive;

    @Column(name = "is_active", columnDefinition = "boolean default true", nullable = false)
    private boolean isActive = true;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private OffsetDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "last_updated_date")
    private OffsetDateTime lastUpdatedDate;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Post> posts;
}
