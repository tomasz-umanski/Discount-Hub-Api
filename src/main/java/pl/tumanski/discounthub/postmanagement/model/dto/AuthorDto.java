package pl.tumanski.discounthub.postmanagement.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Data Transfer Object for Post Author")
public class AuthorDto {
    @Schema(description = "Unique identifier of the author", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Email address of the author", example = "user@example.com")
    private String email;

    @Schema(description = "Username of the author", example = "john_doe")
    private String username;
}
