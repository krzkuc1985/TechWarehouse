package com.github.krzkuc1985.dto.workorderstatus;

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
public class WorkOrderStatusResponse extends AbstractResponse {

    @JsonProperty("status")
    @Schema(description = "Status of the work order", example = "New")
    private String status;

    @Builder
    public WorkOrderStatusResponse(Long id, String status) {
        super(id);
        this.status = status;
    }
}
