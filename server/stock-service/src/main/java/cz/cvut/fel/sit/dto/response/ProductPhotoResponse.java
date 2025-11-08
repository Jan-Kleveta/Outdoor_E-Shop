package cz.cvut.fel.sit.dto.response;

import cz.cvut.fel.sit.enums.PhotoType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "ProductPhoto response DTO")
public class ProductPhotoResponse {

    @Schema(description = "ID of the product photo", example = "1")
    private Long id;

    @Schema(description = "Description of the product photo", example = "Best product photo in the world")
    private String description;

    @Schema(description = "Path of the product photo", example = "/images/product1.jpg")
    private String path;

    @Schema(description = "Type of the product photo", example = "MAIN")
    private PhotoType photoType;
}
