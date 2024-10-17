package com.github.krzkuc1985.rest.workorderstatus.service;

import com.github.krzkuc1985.dto.workorderstatus.WorkOrderStatusRequest;
import com.github.krzkuc1985.dto.workorderstatus.WorkOrderStatusResponse;
import com.github.krzkuc1985.rest.workorderstatus.model.WorkOrderStatus;

import java.util.List;

public interface WorkOrderStatusService {

    WorkOrderStatus findByIdOrThrowException(Long id);

    WorkOrderStatusResponse findById(Long id);

    List<WorkOrderStatusResponse> findAll();

    WorkOrderStatusResponse create(WorkOrderStatusRequest workOrderStatusRequest);

    WorkOrderStatusResponse update(Long id, WorkOrderStatusRequest workOrderStatusRequest);

    void delete(Long id);

}
