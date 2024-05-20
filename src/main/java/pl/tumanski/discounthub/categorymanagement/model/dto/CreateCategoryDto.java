package pl.tumanski.discounthub.categorymanagement.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Data Transfer Object for creating a new category.")
public class CreateCategoryDto {
    @NotBlank(message = "Name is mandatory.")
    @Schema(description = "Category's name", example = "Technology")
    private String name;

    @NotBlank(message = "Url is mandatory.")
    @Schema(description = "Category's name", example = "/technology")
    private String url;

    @NotBlank(message = "Icon is mandatory.")
    @Schema(description = "Category's icon", example = "<i class=\"bi bi-laptop\"></i>")
    private String icon;
}