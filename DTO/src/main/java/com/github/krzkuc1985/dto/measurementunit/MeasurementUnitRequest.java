package com.github.krzkuc1985.dto.measurementunit;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MeasurementUnitRequest {

    @NotBlank
    private String symbol;
}
