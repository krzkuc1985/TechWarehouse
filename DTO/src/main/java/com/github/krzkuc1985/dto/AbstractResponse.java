package com.github.krzkuc1985.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractResponse {

    @JsonProperty("id")
    @Schema(description = "Unique identifier of the entity", example = "1")
    private Long id;

}
