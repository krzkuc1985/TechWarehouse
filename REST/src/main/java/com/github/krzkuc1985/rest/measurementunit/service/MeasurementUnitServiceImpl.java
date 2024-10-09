package com.github.krzkuc1985.rest.measurementunit.service;

import com.github.krzkuc1985.dto.measurementunit.MeasurementUnitRequest;
import com.github.krzkuc1985.dto.measurementunit.MeasurementUnitResponse;
import com.github.krzkuc1985.rest.measurementunit.mapper.MeasurementUnitMapper;
import com.github.krzkuc1985.rest.measurementunit.model.MeasurementUnit;
import com.github.krzkuc1985.rest.measurementunit.repository.MeasurementUnitRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MeasurementUnitServiceImpl implements MeasurementUnitService {

    private final MeasurementUnitRepository measurementUnitRepository;
    private final MeasurementUnitMapper measurementUnitMapper;

    @Override
    public MeasurementUnit findByIdOrThrowException(Long id) {
        return measurementUnitRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public MeasurementUnitResponse findById(Long id) {
        return measurementUnitMapper.mapToResponse(findByIdOrThrowException(id));
    }

    @Override
    public List<MeasurementUnitResponse> findAll() {
        return measurementUnitMapper.mapToResponse(measurementUnitRepository.findAll());
    }

    @Override
    @Transactional
    public MeasurementUnitResponse create(MeasurementUnitRequest measurementUnitRequest) {
        return measurementUnitMapper.mapToResponse(measurementUnitRepository.save(measurementUnitMapper.mapToEntity(measurementUnitRequest)));
    }

    @Override
    @Transactional
    public MeasurementUnitResponse update(Long id, MeasurementUnitRequest measurementUnitRequest) {
        MeasurementUnit measurementUnit = findByIdOrThrowException(id);
        measurementUnit.setSymbol(measurementUnitRequest.getSymbol());
        return measurementUnitMapper.mapToResponse(measurementUnitRepository.save(measurementUnit));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        MeasurementUnit measurementUnit = findByIdOrThrowException(id);
        measurementUnitRepository.delete(measurementUnit);
    }
}
