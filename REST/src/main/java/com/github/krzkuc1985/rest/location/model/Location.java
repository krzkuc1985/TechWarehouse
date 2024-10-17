package com.github.krzkuc1985.rest.location.model;

import com.github.krzkuc1985.rest.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Location extends AbstractEntity {

    @NotBlank
    private String rack;

    @NotBlank
    private String shelf;

}
