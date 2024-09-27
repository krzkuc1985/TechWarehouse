package com.github.krzkuc1985.rest.measurementunit.mapper;

import com.github.krzkuc1985.dto.measurementunit.MeasurementUnitRequest;
import com.github.krzkuc1985.dto.measurementunit.MeasurementUnitResponse;
import com.github.krzkuc1985.rest.measurementunit.model.MeasurementUnit;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MeasurementUnitMapper {

    public MeasurementUnitResponse mapToResponse(MeasurementUnit measurementUnit) {
        return MeasurementUnitResponse.builder()
                .id(measurementUnit.getId())
                .version(measurementUnit.getVersion())
                .symbol(measurementUnit.getSymbol())
                .build();
    }

    public List<MeasurementUnitResponse> mapToResponse(Collection<MeasurementUnit> measurementUnits) {
        return measurementUnits.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public MeasurementUnit mapToEntity(MeasurementUnit measurementUnit, MeasurementUnitRequest measurementUnitRequest) {
        measurementUnit.setSymbol(measurementUnitRequest.getSymbol());
        return measurementUnit;
    }

    public MeasurementUnit mapToEntity(MeasurementUnitRequest measurementUnitRequest) {
        return mapToEntity(new MeasurementUnit(), measurementUnitRequest);
    }
}
