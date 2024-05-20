package pl.tumanski.discounthub.usermanagement.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Data Transfer Object for registering a new user.")
public class CreateUserDto {

    @Email(message = "Provide a valid email address.")
    @NotBlank(message = "Email is mandatory.")
    @Schema(description = "User's email address", example = "user@example.com")
    private String email;

    @NotBlank(message = "Username is mandatory.")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters.")
    @Schema(description = "User's username", example = "john_doe")
    private String username;

    @NotBlank(message = "Password is mandatory.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    @Schema(description = "User's password", example = "strongpassword123")
    private String password;
}
