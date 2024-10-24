package com.github.krzkuc1985.rest.workorder.repository;

import com.github.krzkuc1985.rest.workorder.model.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {

}
