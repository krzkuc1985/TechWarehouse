package com.github.krzkuc1985.rest.workorder.mapper;

import com.github.krzkuc1985.dto.workorder.WorkOrderRequest;
import com.github.krzkuc1985.dto.workorder.WorkOrderResponse;
import com.github.krzkuc1985.rest.employee.repository.EmployeeRepository;
import com.github.krzkuc1985.rest.workorder.model.WorkOrder;
import com.github.krzkuc1985.rest.workorderstatus.repository.WorkOrderStatusRepository;
import com.github.krzkuc1985.rest.workordertype.repository.WorkOrderTypeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WorkOrderMapper {

    private final WorkOrderStatusRepository workOrderStatusRepository;
    private final WorkOrderTypeRepository workOrderTypeRepository;
    private final EmployeeRepository employeeRepository;

    public WorkOrderMapper(WorkOrderStatusRepository workOrderStatusRepository, WorkOrderTypeRepository workOrderTypeRepository, EmployeeRepository employeeRepository) {
        this.workOrderStatusRepository = workOrderStatusRepository;
        this.workOrderTypeRepository = workOrderTypeRepository;
        this.employeeRepository = employeeRepository;
    }

    public WorkOrderResponse mapToResponse(WorkOrder workOrder) {
        return WorkOrderResponse.builder()
                .id(workOrder.getId())
                .description(workOrder.getDescription())
                .startDate(workOrder.getStartDate())
                .endDate(workOrder.getEndDate())
                .workOrderStatusID(workOrder.getStatus().getId())
                .workOrderTypeID(workOrder.getType().getId())
                .employeeID(workOrder.getEmployee().getId())
                .build();
    }

    public List<WorkOrderResponse> mapToResponse(List<WorkOrder> workOrders) {
        return workOrders.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public WorkOrder mapToEntity(WorkOrder workOrder, WorkOrderRequest workOrderRequest) {
        workOrder.setDescription(workOrderRequest.getDescription());
        workOrder.setStartDate(workOrderRequest.getStartDate());
        workOrder.setEndDate(workOrderRequest.getEndDate());
        workOrder.setStatus(workOrderStatusRepository.findById(workOrderRequest.getWorkOrderStatusID()).orElseThrow());
        workOrder.setType(workOrderTypeRepository.findById(workOrderRequest.getWorkOrderTypeID()).orElseThrow());
        workOrder.setEmployee(employeeRepository.findById(workOrderRequest.getEmployeeID()).orElseThrow());
        return workOrder;
    }

    public WorkOrder mapToEntity(WorkOrderRequest workOrderRequest) {
        return mapToEntity(new WorkOrder(), workOrderRequest);
    }

}
