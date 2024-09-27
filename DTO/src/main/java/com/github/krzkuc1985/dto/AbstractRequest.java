package com.github.krzkuc1985.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public abstract class AbstractRequest {

    @JsonProperty("version")
    private Long version;
}
