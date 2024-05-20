package pl.tumanski.discounthub.postmanagement.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@Schema(description = "Data Transfer Object for creating a new post.")
public class CreatePostDto {

    @NotNull(message = "Provide a valid userId")
    @Schema(description = "ID of the user creating the post", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID userId;

    @NotNull(message = "Category ID cannot be null")
    @Schema(description = "ID of the category for the post", example = "1")
    private Long categoryId;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title cannot be longer than 255 characters")
    @Schema(description = "Title of the post", example = "Great Discount on Laptops")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    @Schema(description = "Description of the post", example = "Huge discount on the latest laptops...")
    private String description;

    @NotNull(message = "New price cannot be null")
    @Positive(message = "New price must be positive")
    @Schema(description = "New price of the item", example = "499.99")
    private BigDecimal newPrice;

    @NotNull(message = "Old price cannot be null")
    @Positive(message = "Old price must be positive")
    @Schema(description = "Old price of the item", example = "999.99")
    private BigDecimal oldPrice;

    @NotNull(message = "Delivery price cannot be null")
    @Positive(message = "Delivery price must be positive")
    @Schema(description = "Delivery price for the item", example = "10.00")
    private BigDecimal deliveryPrice;

    @NotBlank(message = "Offer URL cannot be blank")
    @URL(message = "Provide a valid URL")
    @Schema(description = "URL of the offer", example = "https://example.com/offer")
    private String offerUrl;

    @NotBlank(message = "Image URL cannot be blank")
    @Schema(description = "URL of the image", example = "/image.jpg")
    private String imageUrl;

    @NotNull
    @FutureOrPresent(message = "End date must be in the present or future")
    @Schema(description = "End date of the offer", example = "2024-12-31T23:59:59Z")
    private OffsetDateTime endDate;
}
