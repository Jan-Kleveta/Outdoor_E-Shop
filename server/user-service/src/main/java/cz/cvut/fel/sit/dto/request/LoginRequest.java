package cz.cvut.fel.sit.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @Email(message = "Email must be a valid email address")
    @NotBlank(message = "Email must be filled")
    @Schema(description = "User's email address", example = "denny.brown@example.com")
    private String email;

    @NotBlank(message = "Password must be filled")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[.,!@#$&*]).{9,}$",
            message = "Password must be longer than 8 characters and contain at least one uppercase letter and one special character"
    )
    @Size(min = 9, max = 255, message = "Password must be between 9 and 255 characters")
    @Schema(description = "User's password", example = "Password123!")
    private String password;
}