package com.github.krzkuc1985.dto.permission;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.krzkuc1985.dto.AbstractResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionResponse extends AbstractResponse {

    @JsonProperty("name")
    @Schema(description = "Name of the permission", example = "ADD_MEASUREMENT_UNIT")
    private String name;

    @Builder
    public PermissionResponse(Long id, String name) {
        super(id);
        this.name = name;
    }
}
