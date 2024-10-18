package com.github.krzkuc1985.rest.item.model;

import com.github.krzkuc1985.rest.AbstractEntity;
import com.github.krzkuc1985.rest.itemcategory.model.ItemCategory;
import com.github.krzkuc1985.rest.location.model.Location;
import com.github.krzkuc1985.rest.measurementunit.model.MeasurementUnit;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Item extends AbstractEntity {

    @NotBlank
    private String name;

    @NotBlank
    private String orderNumber;

    private String description;

    @NotNull
    @Min(0)
    private Integer quantity;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private MeasurementUnit measurementUnit;

    @NotNull
    @Min(0)
    private Integer minQuantity;

    @NotNull
    @Min(0)
    private Integer maxQuantity;

    @ManyToOne(fetch = FetchType.EAGER)
    private Location location;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private ItemCategory itemCategory;

    private Boolean archive;

}
