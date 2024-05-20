package pl.tumanski.discounthub.usermanagement.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Data Transfer Object for User")
public class UserDto {

    @Schema(description = "Unique identifier of the user", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Email address of the user", example = "user@example.com")
    private String email;

    @Schema(description = "Username of the user", example = "john_doe")
    private String username;

    @Schema(description = "Role of the user")
    private RoleDto role;
}
