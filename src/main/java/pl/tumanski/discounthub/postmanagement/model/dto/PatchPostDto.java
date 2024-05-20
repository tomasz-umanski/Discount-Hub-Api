package pl.tumanski.discounthub.postmanagement.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@Schema(description = "Data Transfer Object for patching a post.")
public class PatchPostDto {

    @Schema(description = "ID of the category for the post", example = "1")
    private Long categoryId;

    @Size(max = 255, message = "Title cannot be longer than 255 characters")
    @Schema(description = "Title of the post", example = "Great Discount on Laptops")
    private String title;

    @Schema(description = "Description of the post", example = "Huge discount on the latest laptops...")
    private String description;

    @Positive(message = "New price must be positive")
    @Schema(description = "New price of the item", example = "499.99")
    private BigDecimal newPrice;

    @Positive(message = "Old price must be positive")
    @Schema(description = "Old price of the item", example = "999.99")
    private BigDecimal oldPrice;

    @Positive(message = "Delivery price must be positive")
    @Schema(description = "Delivery price for the item", example = "10.00")
    private BigDecimal deliveryPrice;

    @URL(message = "Provide a valid URL")
    @Schema(description = "URL of the offer", example = "https://example.com/offer")
    private String offerUrl;

    @Schema(description = "URL of the image", example = "/image.jpg")
    private String imageUrl;

    @FutureOrPresent(message = "End date must be in the present or future")
    @Schema(description = "End date of the offer", example = "2024-12-31T23:59:59Z")
    private OffsetDateTime endDate;
}
