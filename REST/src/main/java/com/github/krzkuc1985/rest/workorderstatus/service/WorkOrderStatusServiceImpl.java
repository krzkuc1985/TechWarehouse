package com.github.krzkuc1985.rest.workorderstatus.service;

import com.github.krzkuc1985.dto.workorderstatus.WorkOrderStatusRequest;
import com.github.krzkuc1985.dto.workorderstatus.WorkOrderStatusResponse;
import com.github.krzkuc1985.rest.workorderstatus.mapper.WorkOrderStatusMapper;
import com.github.krzkuc1985.rest.workorderstatus.model.WorkOrderStatus;
import com.github.krzkuc1985.rest.workorderstatus.repository.WorkOrderStatusRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WorkOrderStatusServiceImpl implements WorkOrderStatusService {

    private final WorkOrderStatusRepository workOrderStatusRepository;
    private final WorkOrderStatusMapper workOrderStatusMapper;

    @Override
    public WorkOrderStatus findByIdOrThrowException(Long id) {
        return workOrderStatusRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public WorkOrderStatusResponse findById(Long id) {
        return workOrderStatusMapper.mapToResponse(findByIdOrThrowException(id));
    }

    @Override
    public List<WorkOrderStatusResponse> findAll() {
        return workOrderStatusMapper.mapToResponse(workOrderStatusRepository.findAll());
    }

    @Override
    @Transactional
    public WorkOrderStatusResponse create(WorkOrderStatusRequest workOrderStatusRequest) {
        return workOrderStatusMapper.mapToResponse(workOrderStatusRepository.save(workOrderStatusMapper.mapToEntity(workOrderStatusRequest)));
    }

    @Override
    @Transactional
    public WorkOrderStatusResponse update(Long id, WorkOrderStatusRequest workOrderStatusRequest) {
        WorkOrderStatus workOrderStatus = findByIdOrThrowException(id);
        workOrderStatus.setStatus(workOrderStatusRequest.getStatus());
        return workOrderStatusMapper.mapToResponse(workOrderStatusRepository.save(workOrderStatus));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        WorkOrderStatus workOrderStatus = findByIdOrThrowException(id);
        workOrderStatusRepository.delete(workOrderStatus);
    }
}
