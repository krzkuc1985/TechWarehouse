package com.github.krzkuc1985.dto.measurementunit;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.krzkuc1985.dto.AbstractResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeasurementUnitResponse extends AbstractResponse {

    @JsonProperty("symbol")
    @Schema(description = "Symbol of the measurement unit", example = "kg")
    private String symbol;

    @Builder
    public MeasurementUnitResponse(Long id, String symbol) {
        super(id);
        this.symbol = symbol;
    }
}

