package com.github.krzkuc1985.dto.address;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressResponse {

    @JsonProperty("country")
    @Schema(description = "Country", example = "USA")
    private String country;

    @JsonProperty("city")
    @Schema(description = "City", example = "New York")
    private String city;

    @JsonProperty("street")
    @Schema(description = "Street", example = "Broadway")
    private String street;

    @JsonProperty("streetNumber")
    @Schema(description = "Street number", example = "17")
    private String streetNumber;

    @JsonProperty("flatNumber")
    @Schema(description = "Flat number", example = "2")
    private String flatNumber;

    @JsonProperty("postalCode")
    @Schema(description = "Postal code", example = "09-001")
    private String postalCode;

}
