package com.github.krzkuc1985.dto.measurementunit;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementUnitRequest {

    @NotBlank(message = "Symbol is mandatory")
    @Schema(description = "Symbol of the measurement unit", example = "kg")
    private String symbol;
}
