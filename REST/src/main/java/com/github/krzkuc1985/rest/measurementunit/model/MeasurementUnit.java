package com.github.krzkuc1985.rest.measurementunit.model;

import com.github.krzkuc1985.rest.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class MeasurementUnit extends AbstractEntity {

    @NotBlank
    @Column(unique = true)
    private String symbol;
}
