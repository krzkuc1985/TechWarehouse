package com.github.krzkuc1985.dto.workordertype;

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
public class WorkOrderTypeResponse extends AbstractResponse {

    @JsonProperty("type")
    @Schema(description = "Type of the work order", example = "Repair")
    private String type;

    @Builder
    public WorkOrderTypeResponse(Long id, String type) {
        super(id);
        this.type = type;
    }
}
