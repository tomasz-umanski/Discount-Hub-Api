package pl.tumanski.discounthub.postmanagement.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Schema(description = "Data Transfer Object for post.")
public class PostDto {
    @Schema(description = "Unique identifier of the post", example = "1")
    private Long id;

    @Schema(description = "Author of the post")
    private AuthorDto author;

    @Schema(description = "Category of the post")
    private PostCategoryDto category;

    @Schema(description = "Title of the post", example = "Great Discount on Laptops")
    private String title;

    @Schema(description = "Description of the post", example = "Huge discount on the latest laptops...")
    private String description;

    @Schema(description = "New price of the item", example = "499.99")
    private BigDecimal newPrice;

    @Schema(description = "Old price of the item", example = "999.99")
    private BigDecimal oldPrice;

    @Schema(description = "Delivery price for the item", example = "10.00")
    private BigDecimal deliveryPrice;

    @Schema(description = "URL of the offer", example = "https://example.com/offer")
    private String offerUrl;

    @Schema(description = "URL of the image", example = "/image.jpg")
    private String imageUrl;

    @Schema(description = "Likes Count of the post", example = "123")
    private Long likesCount;

    @Schema(description = "End date of the offer", example = "2024-12-31T23:59:59Z")
    private OffsetDateTime endDate;
}
