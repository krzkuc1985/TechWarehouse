package com.github.krzkuc1985.dto.location;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequest {

    @NotBlank(message = "Rack is mandatory")
    @Schema(description = "Rack number", example = "A")
    private String rack;

    @NotBlank(message = "Shelf is mandatory")
    @Schema(description = "Shelf number", example = "1")
    private String shelf;

}
