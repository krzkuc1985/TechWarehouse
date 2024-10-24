package com.github.krzkuc1985.dto.workorder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.krzkuc1985.dto.AbstractResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkOrderResponse extends AbstractResponse {

    @JsonProperty("description")
    @Schema(description = "Description of the work order", example = "Replacing a faulty sensor")
    private String description;

    @JsonProperty("startDate")
    @Schema(description = "Start date of the work order", example = "2022-01-10T11:00:00Z")
    private Instant startDate;

    @JsonProperty("endDate")
    @Schema(description = "End date of the work order", example = "2022-01-12T14:00:00Z")
    private Instant endDate;

    @JsonProperty("status")
    @Schema(description = "Status ID of the work order", example = "1")
    private Long workOrderStatusID;

    @JsonProperty("type")
    @Schema(description = "Type ID of the work order", example = "1")
    private Long workOrderTypeID;

    @JsonProperty("employee")
    @Schema(description = "Employee ID of the work order", example = "1")
    private Long employeeID;

    @Builder
    public WorkOrderResponse(Long id, String description, Instant startDate, Instant endDate, Long workOrderStatusID, Long workOrderTypeID, Long employeeID) {
        super(id);
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.workOrderStatusID = workOrderStatusID;
        this.workOrderTypeID = workOrderTypeID;
        this.employeeID = employeeID;
    }
}
