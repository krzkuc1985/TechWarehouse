package com.github.krzkuc1985.rest.workorderstatus.mapper;

import com.github.krzkuc1985.dto.workorderstatus.WorkOrderStatusRequest;
import com.github.krzkuc1985.dto.workorderstatus.WorkOrderStatusResponse;
import com.github.krzkuc1985.rest.workorderstatus.model.WorkOrderStatus;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WorkOrderStatusMapper {

    public WorkOrderStatusResponse mapToResponse(WorkOrderStatus workOrderStatus) {
        return WorkOrderStatusResponse.builder()
                .id(workOrderStatus.getId())
                .status(workOrderStatus.getStatus())
                .build();
    }

    public List<WorkOrderStatusResponse> mapToResponse(Collection<WorkOrderStatus> workOrderStatuses) {
        return workOrderStatuses.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public WorkOrderStatus mapToEntity(WorkOrderStatus workOrderStatus, WorkOrderStatusRequest workOrderStatusRequest) {
        workOrderStatus.setStatus(workOrderStatusRequest.getStatus());
        return workOrderStatus;
    }

    public WorkOrderStatus mapToEntity(WorkOrderStatusRequest workOrderStatusRequest) {
        return mapToEntity(new WorkOrderStatus(), workOrderStatusRequest);
    }

}
