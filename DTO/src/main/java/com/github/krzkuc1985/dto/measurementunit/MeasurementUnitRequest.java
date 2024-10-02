package com.github.krzkuc1985.dto.measurementunit;

import com.github.krzkuc1985.dto.AbstractRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MeasurementUnitRequest extends AbstractRequest {

    @NotBlank(message = "Symbol is mandatory")
    @Schema(description = "Symbol of the measurement unit", example = "kg")
    private String symbol;
}
