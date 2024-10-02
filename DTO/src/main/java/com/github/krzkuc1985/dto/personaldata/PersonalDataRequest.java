package com.github.krzkuc1985.dto.personaldata;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PersonalDataRequest {

    @NotBlank(message = "First name is mandatory")
    @Schema(description = "First name", example = "John")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Schema(description = "Last name", example = "Doe")
    private String lastName;

    @NotBlank(message = "Phone number is mandatory")
    @Schema(description = "Phone number", example = "+48123456789")
    private String phoneNumber;

    @NotBlank(message = "Email is mandatory")
    @Email
    @Schema(description = "Email", example = "j.doe@example.com")
    private String email;

}
