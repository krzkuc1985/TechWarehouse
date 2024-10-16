package com.github.krzkuc1985.dto.employee;

import com.github.krzkuc1985.dto.personaldata.PersonalDataRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

    @NotNull(message = "Personal data is mandatory")
    @Schema(description = "Personal data of the employee")
    private PersonalDataRequest personalDataRequest;

}
