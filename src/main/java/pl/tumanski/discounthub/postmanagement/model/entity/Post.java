package pl.tumanski.discounthub.postmanagement.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;
import pl.tumanski.discounthub.categorymanagement.model.entity.Category;
import pl.tumanski.discounthub.usermanagement.model.entity.User;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "post")
@SQLDelete(sql = "UPDATE post SET is_active = false WHERE id=?")
@SQLRestriction("is_active = true")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User author;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "new_price", nullable = false)
    private BigDecimal newPrice;

    @Column(name = "old_price", nullable = false)
    private BigDecimal oldPrice;

    @Column(name = "delivery_price", nullable = false)
    private BigDecimal deliveryPrice;

    @Column(name = "offer_url", nullable = false)
    private String offerUrl;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "end_date", updatable = false)
    private OffsetDateTime endDate;

    @Column(name = "likes_count", columnDefinition = "boolean default 0", nullable = false)
    private Long likesCount = 0L;

    @Column(name = "is_active", columnDefinition = "boolean default true", nullable = false)
    private boolean isActive = true;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private OffsetDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "last_updated_date")
    private OffsetDateTime lastUpdatedDate;
}
