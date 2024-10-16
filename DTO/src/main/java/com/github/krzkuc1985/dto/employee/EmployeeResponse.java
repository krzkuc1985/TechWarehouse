package com.github.krzkuc1985.dto.employee;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.krzkuc1985.dto.AbstractResponse;
import com.github.krzkuc1985.dto.address.AddressResponse;
import com.github.krzkuc1985.dto.logindata.LoginDataResponse;
import com.github.krzkuc1985.dto.personaldata.PersonalDataResponse;
import com.github.krzkuc1985.dto.role.RoleResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeResponse extends AbstractResponse {

    @JsonProperty("PersonalData")
    @Schema(description = "Personal data of the employee")
    private PersonalDataResponse personalDataResponse;

    @JsonProperty("Address")
    @Schema(description = "Address of the employee")
    private AddressResponse addressResponse;

    @JsonProperty("LoginData")
    @Schema(description = "Login data of the employee")
    private LoginDataResponse loginDataResponse;

    @JsonProperty("Roles")
    @Schema(description = "Roles assigned to the employee")
    private List<RoleResponse> roles;

    @Builder
    public EmployeeResponse(Long id, PersonalDataResponse personalDataResponse, AddressResponse addressResponse, LoginDataResponse loginDataResponse, List<RoleResponse> roles) {
        super(id);
        this.personalDataResponse = personalDataResponse;
        this.addressResponse = addressResponse;
        this.loginDataResponse = loginDataResponse;
        this.roles = roles;
    }
}
