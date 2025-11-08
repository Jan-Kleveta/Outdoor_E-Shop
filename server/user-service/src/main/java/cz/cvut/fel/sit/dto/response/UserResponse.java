package cz.cvut.fel.sit.dto.response;

import cz.cvut.fel.sit.enums.Role;
import cz.cvut.fel.sit.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
public class UserResponse {

    @Schema(description = "Unique identifier of the user", example = "1")
    private Long id;

    @Schema(description = "User email", example = "joe@gmail.com")
    private String email;

    @Schema(description = "User status", example = "REGISTERED")
    private Status status;

    @Schema(description = "User role", example = "CUSTOMER")
    private Role role;
}
