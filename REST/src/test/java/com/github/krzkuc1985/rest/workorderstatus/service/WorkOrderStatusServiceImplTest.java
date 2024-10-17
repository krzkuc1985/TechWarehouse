package com.github.krzkuc1985.rest.workorderstatus.service;


import com.github.krzkuc1985.dto.workorderstatus.WorkOrderStatusRequest;
import com.github.krzkuc1985.dto.workorderstatus.WorkOrderStatusResponse;
import com.github.krzkuc1985.rest.workorderstatus.mapper.WorkOrderStatusMapper;
import com.github.krzkuc1985.rest.workorderstatus.model.WorkOrderStatus;
import com.github.krzkuc1985.rest.workorderstatus.repository.WorkOrderStatusRepository;
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
class WorkOrderStatusServiceImplTest {

    @Mock
    private WorkOrderStatusRepository workOrderStatusRepository;

    @Mock
    private WorkOrderStatusMapper workOrderStatusMapper;

    @InjectMocks
    private WorkOrderStatusServiceImpl workOrderStatusService;

    private WorkOrderStatus workOrderStatus;
    private WorkOrderStatusRequest workOrderStatusRequest;
    private WorkOrderStatusResponse workOrderStatusResponse;

    @BeforeEach
    void setUp() {
        workOrderStatus = new WorkOrderStatus("NEW");
        workOrderStatusRequest = new WorkOrderStatusRequest("NEW");
        workOrderStatusResponse = new WorkOrderStatusResponse(1L, "NEW");
    }

    @Test
    @DisplayName("findById should return WorkOrderStatusResponse when found")
    void findById_ReturnsWorkOrderStatusResponse() {
        when(workOrderStatusRepository.findById(eq(1L))).thenReturn(Optional.of(workOrderStatus));
        when(workOrderStatusMapper.mapToResponse(workOrderStatus)).thenReturn(workOrderStatusResponse);

        WorkOrderStatusResponse result = workOrderStatusService.findById(1L);

        assertEquals(workOrderStatusResponse, result);
        verify(workOrderStatusRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById should throw EntityNotFoundException when not found")
    void findById_ThrowsEntityNotFoundException() {
        when(workOrderStatusRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> workOrderStatusService.findById(1L));
        verify(workOrderStatusRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findAll should return list of WorkOrderStatusResponses")
    void findAll_ReturnsListOfWorkOrderStatusResponses() {
        List<WorkOrderStatus> units = List.of(workOrderStatus);
        List<WorkOrderStatusResponse> responses = List.of(workOrderStatusResponse);

        when(workOrderStatusRepository.findAll()).thenReturn(units);
        when(workOrderStatusMapper.mapToResponse(units)).thenReturn(responses);

        List<WorkOrderStatusResponse> result = workOrderStatusService.findAll();

        assertEquals(responses, result);
        verify(workOrderStatusRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("create should save and return WorkOrderStatusResponse")
    void create_SavesAndReturnsWorkOrderStatusResponse() {
        when(workOrderStatusRepository.save(any(WorkOrderStatus.class))).thenReturn(workOrderStatus);
        when(workOrderStatusMapper.mapToEntity(workOrderStatusRequest)).thenReturn(workOrderStatus);
        when(workOrderStatusMapper.mapToResponse(workOrderStatus)).thenReturn(workOrderStatusResponse);

        WorkOrderStatusResponse result = workOrderStatusService.create(workOrderStatusRequest);

        assertEquals(workOrderStatusResponse, result);
        verify(workOrderStatusRepository, times(1)).save(workOrderStatus);
    }

    @Test
    @DisplayName("update should update and return WorkOrderStatusResponse")
    void update_UpdatesAndReturnsWorkOrderStatusResponse() {
        when(workOrderStatusRepository.findById(eq(1L))).thenReturn(Optional.of(workOrderStatus));
        when(workOrderStatusRepository.save(any(WorkOrderStatus.class))).thenReturn(workOrderStatus);
        when(workOrderStatusMapper.mapToResponse(workOrderStatus)).thenReturn(workOrderStatusResponse);

        WorkOrderStatusResponse result = workOrderStatusService.update(1L, workOrderStatusRequest);

        assertEquals(workOrderStatusResponse, result);
        verify(workOrderStatusRepository, times(1)).findById(1L);
        verify(workOrderStatusRepository, times(1)).save(workOrderStatus);
    }

    @Test
    @DisplayName("update should throw EntityNotFoundException when not found")
    void update_ThrowsEntityNotFoundException() {
        when(workOrderStatusRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> workOrderStatusService.update(1L, workOrderStatusRequest));
        verify(workOrderStatusRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("delete should remove WorkOrderStatus")
    void delete_RemovesWorkOrderStatus() {
        when(workOrderStatusRepository.findById(eq(1L))).thenReturn(Optional.of(workOrderStatus));
        doNothing().when(workOrderStatusRepository).delete(workOrderStatus);

        workOrderStatusService.delete(1L);

        verify(workOrderStatusRepository, times(1)).findById(1L);
        verify(workOrderStatusRepository, times(1)).delete(workOrderStatus);
    }

    @Test
    @DisplayName("delete should throw EntityNotFoundException when not found")
    void delete_ThrowsEntityNotFoundException() {
        when(workOrderStatusRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> workOrderStatusService.delete(1L));
        verify(workOrderStatusRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("update should throw ObjectOptimisticLockingFailureException on optimistic lock failure")
    void update_ThrowsOptimisticLockException() {
        when(workOrderStatusRepository.findById(eq(1L))).thenReturn(Optional.of(workOrderStatus));
        when(workOrderStatusRepository.save(any(WorkOrderStatus.class)))
                .thenThrow(ObjectOptimisticLockingFailureException.class);

        assertThrows(ObjectOptimisticLockingFailureException.class, () -> workOrderStatusService.update(1L, workOrderStatusRequest));
        verify(workOrderStatusRepository, times(1)).findById(1L);
        verify(workOrderStatusRepository, times(1)).save(workOrderStatus);
    }

}
