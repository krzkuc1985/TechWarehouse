package com.github.krzkuc1985.rest.workordertype.service;

import com.github.krzkuc1985.dto.workordertype.WorkOrderTypeRequest;
import com.github.krzkuc1985.dto.workordertype.WorkOrderTypeResponse;
import com.github.krzkuc1985.rest.workordertype.mapper.WorkOrderTypeMapper;
import com.github.krzkuc1985.rest.workordertype.model.WorkOrderType;
import com.github.krzkuc1985.rest.workordertype.repository.WorkOrderTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WorkOrderTypeServiceImpl implements WorkOrderTypeService {

    private final WorkOrderTypeRepository repository;
    private final WorkOrderTypeMapper mapper;

    @Override
    public WorkOrderType findByIdOrThrowException(Long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public WorkOrderTypeResponse findById(Long id) {
        return mapper.mapToResponse(findByIdOrThrowException(id));
    }

    @Override
    public List<WorkOrderTypeResponse> findAll() {
        return mapper.mapToResponse(repository.findAll());
    }

    @Override
    @Transactional
    public WorkOrderTypeResponse create(WorkOrderTypeRequest workOrderTypeRequest) {
        return mapper.mapToResponse(repository.save(mapper.mapToEntity(workOrderTypeRequest)));
    }

    @Override
    @Transactional
    public WorkOrderTypeResponse update(Long id, WorkOrderTypeRequest workOrderTypeRequest) {
        WorkOrderType workOrderType = findByIdOrThrowException(id);
        workOrderType.setType(workOrderTypeRequest.getType());
        return mapper.mapToResponse(repository.save(workOrderType));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        WorkOrderType workOrderType = findByIdOrThrowException(id);
        repository.delete(workOrderType);
    }
}
