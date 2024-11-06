package com.github.krzkuc1985.rest.inventory.model;

import com.github.krzkuc1985.rest.AbstractEntity;
import com.github.krzkuc1985.rest.item.model.Item;
import com.github.krzkuc1985.rest.workorder.model.WorkOrder;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Inventory extends AbstractEntity {

    @ManyToOne
    private WorkOrder workOrder;

    @ManyToOne
    private Item item;

    @NotNull
    private Integer quantity;

    @NotNull
    private Instant date;
}
