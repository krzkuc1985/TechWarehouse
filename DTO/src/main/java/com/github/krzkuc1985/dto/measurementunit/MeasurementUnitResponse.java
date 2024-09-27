package com.github.krzkuc1985.dto.measurementunit;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.krzkuc1985.dto.AbstractResponse;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeasurementUnitResponse extends AbstractResponse {

    @JsonProperty("symbol")
    private String symbol;

    @Builder
    public MeasurementUnitResponse(Long id, Long version, String symbol) {
        super(id, version);
        this.symbol = symbol;
    }
}

