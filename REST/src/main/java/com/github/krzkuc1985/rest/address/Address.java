package com.github.krzkuc1985.rest.address;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Embeddable
public class Address {

    @NotBlank
    private String country;

    @NotBlank
    private String city;

    @NotBlank
    private String street;

    @NotBlank
    private String streetNumber;

    @NotNull
    private String flatNumber;

    @NotBlank
    private String postalCode;

}
