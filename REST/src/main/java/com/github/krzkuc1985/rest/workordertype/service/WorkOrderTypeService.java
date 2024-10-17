package com.github.krzkuc1985.rest.workordertype.service;

import com.github.krzkuc1985.dto.workordertype.WorkOrderTypeRequest;
import com.github.krzkuc1985.dto.workordertype.WorkOrderTypeResponse;
import com.github.krzkuc1985.rest.workordertype.model.WorkOrderType;

import java.util.List;

public interface WorkOrderTypeService {

    WorkOrderType findByIdOrThrowException(Long id);

    WorkOrderTypeResponse findById(Long id);

    List<WorkOrderTypeResponse> findAll();

    WorkOrderTypeResponse create(WorkOrderTypeRequest workOrderTypeRequest);

    WorkOrderTypeResponse update(Long id, WorkOrderTypeRequest workOrderTypeRequest);

    void delete(Long id);

}
