package com.github.krzkuc1985.dto.role;

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
public class RoleResponse extends AbstractResponse {

    @JsonProperty("name")
    @Schema(description = "Name of the role", example = "ADMIN")
    private String name;

    @Builder
    public RoleResponse(Long id, String name) {
        super(id);
        this.name = name;
    }
}
