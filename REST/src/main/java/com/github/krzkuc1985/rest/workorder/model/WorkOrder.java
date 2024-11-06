package com.github.krzkuc1985.rest.workorder.model;

import com.github.krzkuc1985.rest.AbstractEntity;
import com.github.krzkuc1985.rest.employee.model.Employee;
import com.github.krzkuc1985.rest.inventory.model.Inventory;
import com.github.krzkuc1985.rest.workorderstatus.model.WorkOrderStatus;
import com.github.krzkuc1985.rest.workordertype.model.WorkOrderType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class WorkOrder extends AbstractEntity {

    @NotBlank
    private String description;

    private Instant startDate;

    private Instant endDate;

    @ManyToOne
    private WorkOrderStatus status;

    @ManyToOne
    private WorkOrderType type;

    @ManyToOne
    private Employee employee;

    @OneToMany(mappedBy = "workOrder")
    private List<Inventory> inventories;

}
