package com.github.krzkuc1985.dto.workorderstatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderStatusRequest {

    @NotBlank(message = "Status is mandatory")
    @Schema(description = "Status of the work order", example = "New")
    private String status;
}
