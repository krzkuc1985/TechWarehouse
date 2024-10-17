package com.github.krzkuc1985.rest.workordertype.service;

import com.github.krzkuc1985.dto.workordertype.WorkOrderTypeRequest;
import com.github.krzkuc1985.dto.workordertype.WorkOrderTypeResponse;
import com.github.krzkuc1985.rest.workordertype.mapper.WorkOrderTypeMapper;
import com.github.krzkuc1985.rest.workordertype.model.WorkOrderType;
import com.github.krzkuc1985.rest.workordertype.repository.WorkOrderTypeRepository;
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
class WorkOrderTypeServiceImplTest {

    @Mock
    private WorkOrderTypeRepository workOrderTypeRepository;

    @Mock
    private WorkOrderTypeMapper workOrderTypeMapper;

    @InjectMocks
    private WorkOrderTypeServiceImpl workOrderTypeService;

    private WorkOrderType workOrderType;
    private WorkOrderTypeRequest workOrderTypeRequest;
    private WorkOrderTypeResponse workOrderTypeResponse;

    @BeforeEach
    void setUp() {
        workOrderType = new WorkOrderType("Repair_Test");
        workOrderTypeRequest = new WorkOrderTypeRequest("Repair_Test");
        workOrderTypeResponse = new WorkOrderTypeResponse(1L, "Repair_Test");
    }

    @Test
    @DisplayName("findById should return WorkOrderTypeResponse when found")
    void findById_ReturnsWorkOrderTypeResponse() {
        when(workOrderTypeRepository.findById(eq(1L))).thenReturn(Optional.of(workOrderType));
        when(workOrderTypeMapper.mapToResponse(workOrderType)).thenReturn(workOrderTypeResponse);

        WorkOrderTypeResponse result = workOrderTypeService.findById(1L);

        assertEquals(workOrderTypeResponse, result);
        verify(workOrderTypeRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById should throw EntityNotFoundException when not found")
    void findById_ThrowsEntityNotFoundException() {
        when(workOrderTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> workOrderTypeService.findById(1L));
        verify(workOrderTypeRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findAll should return list of WorkOrderTypeResponses")
    void findAll_ReturnsListOfWorkOrderTypeResponses() {
        List<WorkOrderType> units = List.of(workOrderType);
        List<WorkOrderTypeResponse> responses = List.of(workOrderTypeResponse);

        when(workOrderTypeRepository.findAll()).thenReturn(units);
        when(workOrderTypeMapper.mapToResponse(units)).thenReturn(responses);

        List<WorkOrderTypeResponse> result = workOrderTypeService.findAll();

        assertEquals(responses, result);
        verify(workOrderTypeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("create should save and return WorkOrderTypeResponse")
    void create_SavesAndReturnsWorkOrderTypeResponse() {
        when(workOrderTypeRepository.save(any(WorkOrderType.class))).thenReturn(workOrderType);
        when(workOrderTypeMapper.mapToEntity(workOrderTypeRequest)).thenReturn(workOrderType);
        when(workOrderTypeMapper.mapToResponse(workOrderType)).thenReturn(workOrderTypeResponse);

        WorkOrderTypeResponse result = workOrderTypeService.create(workOrderTypeRequest);

        assertEquals(workOrderTypeResponse, result);
        verify(workOrderTypeRepository, times(1)).save(workOrderType);
    }

    @Test
    @DisplayName("update should update and return WorkOrderTypeResponse")
    void update_UpdatesAndReturnsWorkOrderTypeResponse() {
        when(workOrderTypeRepository.findById(eq(1L))).thenReturn(Optional.of(workOrderType));
        when(workOrderTypeRepository.save(any(WorkOrderType.class))).thenReturn(workOrderType);
        when(workOrderTypeMapper.mapToResponse(workOrderType)).thenReturn(workOrderTypeResponse);

        WorkOrderTypeResponse result = workOrderTypeService.update(1L, workOrderTypeRequest);

        assertEquals(workOrderTypeResponse, result);
        verify(workOrderTypeRepository, times(1)).findById(1L);
        verify(workOrderTypeRepository, times(1)).save(workOrderType);
    }

    @Test
    @DisplayName("update should throw EntityNotFoundException when not found")
    void update_ThrowsEntityNotFoundException() {
        when(workOrderTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> workOrderTypeService.update(1L, workOrderTypeRequest));
        verify(workOrderTypeRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("delete should remove WorkOrderType")
    void delete_RemovesWorkOrderType() {
        when(workOrderTypeRepository.findById(eq(1L))).thenReturn(Optional.of(workOrderType));
        doNothing().when(workOrderTypeRepository).delete(workOrderType);

        workOrderTypeService.delete(1L);

        verify(workOrderTypeRepository, times(1)).findById(1L);
        verify(workOrderTypeRepository, times(1)).delete(workOrderType);
    }

    @Test
    @DisplayName("delete should throw EntityNotFoundException when not found")
    void delete_ThrowsEntityNotFoundException() {
        when(workOrderTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> workOrderTypeService.delete(1L));
        verify(workOrderTypeRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("update should throw ObjectOptimisticLockingFailureException on optimistic lock failure")
    void update_ThrowsOptimisticLockException() {
        when(workOrderTypeRepository.findById(eq(1L))).thenReturn(Optional.of(workOrderType));
        when(workOrderTypeRepository.save(any(WorkOrderType.class)))
                .thenThrow(ObjectOptimisticLockingFailureException.class);

        assertThrows(ObjectOptimisticLockingFailureException.class, () -> workOrderTypeService.update(1L, workOrderTypeRequest));
        verify(workOrderTypeRepository, times(1)).findById(1L);
        verify(workOrderTypeRepository, times(1)).save(workOrderType);
    }

}
