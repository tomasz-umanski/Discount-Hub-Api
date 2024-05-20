package pl.tumanski.discounthub.usermanagement.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Data Transfer Object for Role")
public class RoleDto {
    @Schema(description = "Name of the role", example = "admin")
    private String name;
}
