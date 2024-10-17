package com.github.krzkuc1985.dto.location;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.krzkuc1985.dto.AbstractResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationResponse extends AbstractResponse {

    @JsonProperty("rack")
    @Schema(description = "Rack number", example = "A")
    private String rack;

    @JsonProperty("shelf")
    @Schema(description = "Shelf number", example = "1")
    private String shelf;

    @Builder
    public LocationResponse(Long id, String rack, String shelf) {
        super(id);
        this.rack = rack;
        this.shelf = shelf;
    }
}
