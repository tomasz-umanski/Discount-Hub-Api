package pl.tumanski.discounthub.categorymanagement.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Data Transfer Object for category.")
public class CategoryDto {
    @Schema(description = "Unique identifier of the category", example = "1")
    private Long id;

    @Schema(description = "Category's name", example = "Technology")
    private String name;

    @Schema(description = "Category's name", example = "/technology")
    private String url;

    @Schema(description = "Category's icon", example = "<i class=\"bi bi-laptop\"></i>")
    private String icon;
}
