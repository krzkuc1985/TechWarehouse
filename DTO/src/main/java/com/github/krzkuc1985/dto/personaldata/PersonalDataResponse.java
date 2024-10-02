package com.github.krzkuc1985.dto.personaldata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonalDataResponse {

    @JsonProperty("firstName")
    @Schema(description = "First name", example = "John")
    private String firstName;

    @JsonProperty("lastName")
    @Schema(description = "Last name", example = "Doe")
    private String lastName;

    @JsonProperty("phoneNumber")
    @Schema(description = "Phone number", example = "+48123456789")
    private String phoneNumber;

    @JsonProperty("email")
    @Schema(description = "Email", example = "j.doe@example.com")
    private String email;

}
