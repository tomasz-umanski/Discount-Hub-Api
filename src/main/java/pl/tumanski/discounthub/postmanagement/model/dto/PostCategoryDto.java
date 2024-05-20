package pl.tumanski.discounthub.postmanagement.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Data Transfer Object for post's category.")
public class PostCategoryDto {
    @Schema(description = "Unique identifier of the post's category", example = "1")
    private Long id;

    @Schema(description = "Post's category name", example = "Technology")
    private String name;
}
