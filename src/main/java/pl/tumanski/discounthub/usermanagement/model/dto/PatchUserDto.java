package pl.tumanski.discounthub.usermanagement.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Data Transfer Object for patching a user.")
public class PatchUserDto {

    @Email(message = "Provide a valid email address.")
    @Schema(description = "User's email address", example = "user@example.com")
    private String email;

    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters.")
    @Schema(description = "User's username", example = "john_doe")
    private String username;

    @Size(min = 8, message = "Password must be at least 8 characters long.")
    @Schema(description = "User's password", example = "strongpassword123")
    private String password;
}
