package com.github.krzkuc1985.dto.address;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AddressRequest {

    @NotBlank(message = "Country is mandatory")
    @Schema(description = "Country", example = "USA")
    private String country;

    @NotBlank(message = "City is mandatory")
    @Schema(description = "City", example = "New York")
    private String city;

    @NotBlank(message = "Street is mandatory")
    @Schema(description = "Street", example = "Broadway")
    private String street;

    @NotBlank(message = "Street number is mandatory")
    @Schema(description = "Street number", example = "17")
    private String streetNumber;

    @NotNull(message = "Flat number is mandatory")
    @Schema(description = "Flat number", example = "2")
    private String flatNumber;

    @NotBlank(message = "Postal code is mandatory")
    @Schema(description = "Postal code", example = "09-001")
    private String postalCode;

}
