package com.github.krzkuc1985.dto.workordertype;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderTypeRequest {

    @NotBlank(message = "Type is mandatory")
    @Schema(description = "Type of the work order", example = "Repair")
    private String type;
}
