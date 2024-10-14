package com.github.krzkuc1985.rest.measurementunit.service;

import com.github.krzkuc1985.dto.measurementunit.MeasurementUnitRequest;
import com.github.krzkuc1985.dto.measurementunit.MeasurementUnitResponse;
import com.github.krzkuc1985.rest.measurementunit.mapper.MeasurementUnitMapper;
import com.github.krzkuc1985.rest.measurementunit.model.MeasurementUnit;
import com.github.krzkuc1985.rest.measurementunit.repository.MeasurementUnitRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MeasurementUnitServiceImplTest {

    @Mock
    private MeasurementUnitRepository measurementUnitRepository;

    @Mock
    private MeasurementUnitMapper measurementUnitMapper;

    @InjectMocks
    private MeasurementUnitServiceImpl measurementUnitService;

    private MeasurementUnit measurementUnit;
    private MeasurementUnitRequest measurementUnitRequest;
    private MeasurementUnitResponse measurementUnitResponse;

    @BeforeEach
    void setUp() {
        measurementUnit = new MeasurementUnit("kg");
        measurementUnitRequest = new MeasurementUnitRequest("kg");
        measurementUnitResponse = new MeasurementUnitResponse(1L, "kg");
    }

    @Test
    @DisplayName("findById should return MeasurementUnitResponse when found")
    void findById_ReturnsMeasurementUnitResponse() {
        when(measurementUnitRepository.findById(eq(1L))).thenReturn(Optional.of(measurementUnit));
        when(measurementUnitMapper.mapToResponse(measurementUnit)).thenReturn(measurementUnitResponse);

        MeasurementUnitResponse result = measurementUnitService.findById(1L);

        assertEquals(measurementUnitResponse, result);
        verify(measurementUnitRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById should throw EntityNotFoundException when not found")
    void findById_ThrowsEntityNotFoundException() {
        when(measurementUnitRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> measurementUnitService.findById(1L));
        verify(measurementUnitRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findAll should return list of MeasurementUnitResponses")
    void findAll_ReturnsListOfMeasurementUnitResponses() {
        List<MeasurementUnit> units = List.of(measurementUnit);
        List<MeasurementUnitResponse> responses = List.of(measurementUnitResponse);

        when(measurementUnitRepository.findAll()).thenReturn(units);
        when(measurementUnitMapper.mapToResponse(units)).thenReturn(responses);

        List<MeasurementUnitResponse> result = measurementUnitService.findAll();

        assertEquals(responses, result);
        verify(measurementUnitRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("create should save and return MeasurementUnitResponse")
    void create_SavesAndReturnsMeasurementUnitResponse() {
        when(measurementUnitRepository.save(any(MeasurementUnit.class))).thenReturn(measurementUnit);
        when(measurementUnitMapper.mapToEntity(measurementUnitRequest)).thenReturn(measurementUnit);
        when(measurementUnitMapper.mapToResponse(measurementUnit)).thenReturn(measurementUnitResponse);

        MeasurementUnitResponse result = measurementUnitService.create(measurementUnitRequest);

        assertEquals(measurementUnitResponse, result);
        verify(measurementUnitRepository, times(1)).save(measurementUnit);
    }

    @Test
    @DisplayName("update should update and return MeasurementUnitResponse")
    void update_UpdatesAndReturnsMeasurementUnitResponse() {
        when(measurementUnitRepository.findById(eq(1L))).thenReturn(Optional.of(measurementUnit));
        when(measurementUnitRepository.save(any(MeasurementUnit.class))).thenReturn(measurementUnit);
        when(measurementUnitMapper.mapToResponse(measurementUnit)).thenReturn(measurementUnitResponse);

        MeasurementUnitResponse result = measurementUnitService.update(1L, measurementUnitRequest);

        assertEquals(measurementUnitResponse, result);
        verify(measurementUnitRepository, times(1)).findById(1L);
        verify(measurementUnitRepository, times(1)).save(measurementUnit);
    }

    @Test
    @DisplayName("update should throw EntityNotFoundException when not found")
    void update_ThrowsEntityNotFoundException() {
        when(measurementUnitRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> measurementUnitService.update(1L, measurementUnitRequest));
        verify(measurementUnitRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("delete should remove MeasurementUnit")
    void delete_RemovesMeasurementUnit() {
        when(measurementUnitRepository.findById(eq(1L))).thenReturn(Optional.of(measurementUnit));
        doNothing().when(measurementUnitRepository).delete(measurementUnit);

        measurementUnitService.delete(1L);

        verify(measurementUnitRepository, times(1)).findById(1L);
        verify(measurementUnitRepository, times(1)).delete(measurementUnit);
    }

    @Test
    @DisplayName("delete should throw EntityNotFoundException when not found")
    void delete_ThrowsEntityNotFoundException() {
        when(measurementUnitRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> measurementUnitService.delete(1L));
        verify(measurementUnitRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("update should throw ObjectOptimisticLockingFailureException on optimistic lock failure")
    void update_ThrowsOptimisticLockException() {
        when(measurementUnitRepository.findById(eq(1L))).thenReturn(Optional.of(measurementUnit));
        when(measurementUnitRepository.save(any(MeasurementUnit.class)))
                .thenThrow(ObjectOptimisticLockingFailureException.class);

        assertThrows(ObjectOptimisticLockingFailureException.class, () -> measurementUnitService.update(1L, measurementUnitRequest));
        verify(measurementUnitRepository, times(1)).findById(1L);
        verify(measurementUnitRepository, times(1)).save(measurementUnit);
    }
}
