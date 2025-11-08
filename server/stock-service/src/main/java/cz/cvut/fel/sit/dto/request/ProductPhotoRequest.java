package cz.cvut.fel.sit.dto.request;

import cz.cvut.fel.sit.enums.PhotoType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Create product photo request")
public class ProductPhotoRequest {

    @Schema(description = "Description", example = "Ultrasoft cotton fabric")
    private String description;

    @NotBlank(message = "Path must be filled")
    @Schema(description = "Path to the photo", example = "/stock/api/images/2d3f8e54-e1e6-48d3-a8a1-d37bd525796d_shrinked2.JPG")
    private String path;

    @Schema(description = "Photo type", example = "MAIN")
    private PhotoType photoType;
}
