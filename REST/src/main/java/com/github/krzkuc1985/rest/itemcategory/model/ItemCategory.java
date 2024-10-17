package com.github.krzkuc1985.rest.itemcategory.model;

import com.github.krzkuc1985.rest.AbstractEntity;
import jakarta.persistence.Column;
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
public class ItemCategory extends AbstractEntity {

    @NotBlank
    @Column(unique = true)
    private String name;

}