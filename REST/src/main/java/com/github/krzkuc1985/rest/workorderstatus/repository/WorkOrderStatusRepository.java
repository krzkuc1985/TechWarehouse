package com.github.krzkuc1985.rest.workorderstatus.repository;

import com.github.krzkuc1985.rest.workorderstatus.model.WorkOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOrderStatusRepository extends JpaRepository<WorkOrderStatus, Long> {

}
