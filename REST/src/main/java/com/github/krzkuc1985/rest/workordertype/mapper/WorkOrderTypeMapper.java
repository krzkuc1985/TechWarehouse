package com.github.krzkuc1985.rest.workordertype.mapper;

import com.github.krzkuc1985.dto.workordertype.WorkOrderTypeRequest;
import com.github.krzkuc1985.dto.workordertype.WorkOrderTypeResponse;
import com.github.krzkuc1985.rest.workordertype.model.WorkOrderType;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WorkOrderTypeMapper {

    public WorkOrderTypeResponse mapToResponse(WorkOrderType workOrderType) {
        return WorkOrderTypeResponse.builder()
                .id(workOrderType.getId())
                .type(workOrderType.getType())
                .build();
    }

    public List<WorkOrderTypeResponse> mapToResponse(Collection<WorkOrderType> workOrderTypes) {
        return workOrderTypes.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public WorkOrderType mapToEntity(WorkOrderType workOrderType, WorkOrderTypeRequest workOrderTypeRequest) {
        workOrderType.setType(workOrderTypeRequest.getType());
        return workOrderType;
    }

    public WorkOrderType mapToEntity(WorkOrderTypeRequest workOrderTypeRequest) {
        return mapToEntity(new WorkOrderType(), workOrderTypeRequest);
    }

}
