package com.github.krzkuc1985.rest.measurementunit.model;

import com.github.krzkuc1985.rest.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class MeasurementUnit extends AbstractEntity {

    @NotBlank
    private String symbol;
}
