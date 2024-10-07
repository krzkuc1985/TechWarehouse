package com.github.krzkuc1985.dto.role;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.krzkuc1985.dto.AbstractResponse;
import com.github.krzkuc1985.dto.permission.PermissionResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleResponse extends AbstractResponse {

    @JsonProperty("name")
    @Schema(description = "Name of the role", example = "ADMIN")
    private String name;

    @JsonProperty("permissions")
    @Schema(description = "Permissions assigned to the role")
    private List<PermissionResponse> permissions;

    @Builder
    public RoleResponse(Long id, String name, List<PermissionResponse> permissions) {
        super(id);
        this.name = name;
        this.permissions = permissions;
    }
}
