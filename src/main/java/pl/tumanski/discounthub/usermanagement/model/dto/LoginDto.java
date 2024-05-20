package pl.tumanski.discounthub.usermanagement.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Data Transfer Object for authenticating as a user.")
public class LoginDto {

    @NotBlank(message = "Username or email is mandatory.")
    @Schema(description = "User's identifier", example = "user@example.com")
    private String identifier;

    @NotBlank(message = "Password is mandatory.")
    @Schema(description = "User's password", example = "strongpassword123")
    private String password;
}