package pl.tumanski.discounthub.categorymanagement.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Data Transfer Object for patching a category.")
public class PatchCategoryDto {

    @Schema(description = "Category's name", example = "Technology")
    private String name;

    @Schema(description = "Category's name", example = "/technology")
    private String url;

    @Schema(description = "Category's icon", example = "<i class=\"bi bi-laptop\"></i>")
    private String icon;
}
