package com.github.krzkuc1985.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class AbstractResponse {

    @JsonProperty("id")
    private Long id;
}
