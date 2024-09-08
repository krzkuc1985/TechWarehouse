package com.github.krzkuc1985.rest.measurementunit.service;

import com.github.krzkuc1985.dto.measurementunit.MeasurementUnitRequest;
import com.github.krzkuc1985.dto.measurementunit.MeasurementUnitResponse;
import com.github.krzkuc1985.rest.measurementunit.model.MeasurementUnit;

import java.util.List;

public interface MeasurementUnitService {

    MeasurementUnit findByIdOrThrowException(Long id);

    MeasurementUnitResponse findById(Long id);

    List<MeasurementUnitResponse> findAll();

    MeasurementUnitResponse create(MeasurementUnitRequest measurementUnitRequest);

    MeasurementUnitResponse update(Long id, MeasurementUnitRequest measurementUnitRequest);

    void delete(Long id);

}
