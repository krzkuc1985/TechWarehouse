package com.github.krzkuc1985.dto.workorder;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderRequest {

    @NotBlank(message = "Description is mandatory")
    @Schema(description = "Description of the work order", example = "Replacing a faulty sensor")
    private String description;

    @Schema(description = "Start date of the work order", example = "2022-01-10T11:00:00Z")
    private Instant startDate;

    @Schema(description = "End date of the work order", example = "2022-01-12T14:00:00Z")
    private Instant endDate;

    @NotNull(message = "Status ID is mandatory")
    @Schema(description = "Status ID of the work order", example = "1")
    private Long workOrderStatusID;

    @NotNull(message = "Type ID is mandatory")
    @Schema(description = "Type ID of the work order", example = "1")
    private Long workOrderTypeID;

    @NotNull(message = "Employee ID is mandatory")
    @Schema(description = "Employee ID of the work order", example = "1")
    private Long employeeID;

}
