package com.github.krzkuc1985.dto.measurementunit;

import com.github.krzkuc1985.dto.AbstractRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MeasurementUnitRequest extends AbstractRequest {

    @NotBlank(message = "Symbol is mandatory")
    private String symbol;
}
