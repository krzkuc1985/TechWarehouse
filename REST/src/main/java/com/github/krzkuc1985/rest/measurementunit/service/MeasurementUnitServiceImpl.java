package com.github.krzkuc1985.rest.measurementunit.service;

import com.github.krzkuc1985.dto.measurementunit.MeasurementUnitRequest;
import com.github.krzkuc1985.dto.measurementunit.MeasurementUnitResponse;
import com.github.krzkuc1985.rest.measurementunit.mapper.MeasurementUnitMapper;
import com.github.krzkuc1985.rest.measurementunit.model.MeasurementUnit;
import com.github.krzkuc1985.rest.measurementunit.repository.MeasurementUnitRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MeasurementUnitServiceImpl implements MeasurementUnitService {

    private final MeasurementUnitRepository repository;
    private final MeasurementUnitMapper mapper;

    @Override
    public MeasurementUnit findByIdOrThrowException(Long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public MeasurementUnitResponse findById(Long id) {
        return mapper.mapToResponse(findByIdOrThrowException(id));
    }

    @Override
    public List<MeasurementUnitResponse> findAll() {
        return mapper.mapToResponse(repository.findAll());
    }

    @Override
    public MeasurementUnitResponse create(MeasurementUnitRequest measurementUnitRequest) {
        return mapper.mapToResponse(repository.save(mapper.mapToEntity(measurementUnitRequest)));
    }

    @Override
    public MeasurementUnitResponse update(Long id, MeasurementUnitRequest measurementUnitRequest) {
        MeasurementUnit measurementUnit = findByIdOrThrowException(id);
        measurementUnit.setSymbol(measurementUnitRequest.getSymbol());
        return mapper.mapToResponse(repository.save(measurementUnit));
    }

    @Override
    public void delete(Long id) {
        MeasurementUnit measurementUnit = findByIdOrThrowException(id);
        repository.delete(measurementUnit);
    }
}
