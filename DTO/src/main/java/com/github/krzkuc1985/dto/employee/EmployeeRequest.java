package com.github.krzkuc1985.dto.employee;

import com.github.krzkuc1985.dto.address.AddressRequest;
import com.github.krzkuc1985.dto.logindata.LoginDataRequest;
import com.github.krzkuc1985.dto.personaldata.PersonalDataRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmployeeRequest {

    @NotBlank(message = "Personal data is mandatory")
    @Schema(description = "Personal data of the employee")
    private PersonalDataRequest personalDataRequest;

    @NotBlank(message = "Address is mandatory")
    @Schema(description = "Address of the employee")
    private AddressRequest addressRequest;

    @NotBlank(message = "Login data is mandatory")
    @Schema(description = "Login data of the employee")
    private LoginDataRequest loginDataRequest;

}
