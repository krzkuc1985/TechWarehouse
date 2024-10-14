package com.github.krzkuc1985.dto.permission;

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
public class PermissionResponse extends AbstractResponse {

    @JsonProperty("name")
    @Schema(description = "Name of the permission", example = "ADD_MEASUREMENT_UNIT")
    private String name;

    @JsonProperty("category")
    @Schema(description = "Category of the permission", example = "MEASUREMENT_UNIT")
    private String category;

    @Builder
    public PermissionResponse(Long id, String name, String category) {
        super(id);
        this.name = name;
        this.category = category;
    }
}
