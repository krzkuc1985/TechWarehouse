package com.github.krzkuc1985.rest.workorder.service;

import com.github.krzkuc1985.dto.workorder.WorkOrderRequest;
import com.github.krzkuc1985.dto.workorder.WorkOrderResponse;
import com.github.krzkuc1985.rest.address.Address;
import com.github.krzkuc1985.rest.employee.model.Employee;
import com.github.krzkuc1985.rest.inventory.model.Inventory;
import com.github.krzkuc1985.rest.logindata.LoginData;
import com.github.krzkuc1985.rest.personaldata.PersonalData;
import com.github.krzkuc1985.rest.role.model.Role;
import com.github.krzkuc1985.rest.workorder.mapper.WorkOrderMapper;
import com.github.krzkuc1985.rest.workorder.model.WorkOrder;
import com.github.krzkuc1985.rest.workorder.repository.WorkOrderRepository;
import com.github.krzkuc1985.rest.workorderstatus.model.WorkOrderStatus;
import com.github.krzkuc1985.rest.workordertype.model.WorkOrderType;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class WorkOrderServiceImplTest {

    @Mock
    private WorkOrderRepository workOrderRepository;

    @Mock
    private WorkOrderMapper workOrderMapper;

    @InjectMocks
    private WorkOrderServiceImpl workOrderService;

    private WorkOrder workOrder;
    private WorkOrderRequest workOrderRequest;
    private WorkOrderResponse workOrderResponse;

    @BeforeEach
    void setUp() {
        WorkOrderStatus status = new WorkOrderStatus("New_Test");
        WorkOrderType type = new WorkOrderType("Repair_Test");
        PersonalData personalData = new PersonalData("John", "Doe", "555888999", "example@email.com");
        Address address = new Address("USA", "New York", "Broadway", "1", "", "10001");
        LoginData loginData = new LoginData("admin", "password123");
        Role role = new Role("EMPLOYEE", new HashSet<>());
        Employee employee = new Employee(personalData, address, loginData, new ArrayList<>(List.of(role)));
        List<Inventory> inventories = List.of();
        workOrder = new WorkOrder("Replacing a faulty sensor", Instant.parse("2022-01-10T11:00:00Z"), Instant.parse("2022-01-12T14:00:00Z"), status, type, employee, inventories);
        workOrderRequest = new WorkOrderRequest("Replacing a faulty sensor", Instant.parse("2022-01-10T11:00:00Z"), Instant.parse("2022-01-12T14:00:00Z"), 1L, 1L, 1L);
        workOrderResponse = new WorkOrderResponse(1L, "Replacing a faulty sensor", Instant.parse("2022-01-10T11:00:00Z"), Instant.parse("2022-01-12T14:00:00Z"), 1L, 1L, 1L);
    }

    @Test
    @DisplayName("findById should return WorkOrderResponse when found")
    void findById_ReturnsWorkOrderResponse() {
        when(workOrderRepository.findById(eq(1L))).thenReturn(Optional.of(workOrder));
        when(workOrderMapper.mapToResponse(workOrder)).thenReturn(workOrderResponse);

        WorkOrderResponse result = workOrderService.findById(1L);

        assertEquals(workOrderResponse, result);
        verify(workOrderRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById should throw EntityNotFoundException when not found")
    void findById_ThrowsEntityNotFoundException() {
        when(workOrderRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> workOrderService.findById(1L));
        verify(workOrderRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findAll should return list of WorkOrderResponses")
    void findAll_ReturnsListOfWorkOrderResponses() {
        List<WorkOrder> units = List.of(workOrder);
        List<WorkOrderResponse> responses = List.of(workOrderResponse);

        when(workOrderRepository.findAll()).thenReturn(units);
        when(workOrderMapper.mapToResponse(units)).thenReturn(responses);

        List<WorkOrderResponse> result = workOrderService.findAll();

        assertEquals(responses, result);
        verify(workOrderRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("create should save and return WorkOrderResponse")
    void create_SavesAndReturnsWorkOrderResponse() {
        when(workOrderRepository.save(any(WorkOrder.class))).thenReturn(workOrder);
        when(workOrderMapper.mapToEntity(workOrderRequest)).thenReturn(workOrder);
        when(workOrderMapper.mapToResponse(workOrder)).thenReturn(workOrderResponse);

        WorkOrderResponse result = workOrderService.create(workOrderRequest);

        assertEquals(workOrderResponse, result);
        verify(workOrderRepository, times(1)).save(workOrder);
    }

    @Test
    @DisplayName("update should update and return WorkOrderResponse")
    void update_UpdatesAndReturnsWorkOrderResponse() {
        when(workOrderRepository.findById(eq(1L))).thenReturn(Optional.of(workOrder));
        when(workOrderRepository.save(any(WorkOrder.class))).thenReturn(workOrder);
        when(workOrderMapper.mapToResponse(workOrder)).thenReturn(workOrderResponse);

        WorkOrderResponse result = workOrderService.update(1L, workOrderRequest);

        assertEquals(workOrderResponse, result);
        verify(workOrderRepository, times(1)).findById(1L);
        verify(workOrderRepository, times(1)).save(workOrder);
    }

    @Test
    @DisplayName("update should throw EntityNotFoundException when not found")
    void update_ThrowsEntityNotFoundException() {
        when(workOrderRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> workOrderService.update(1L, workOrderRequest));
        verify(workOrderRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("delete should remove WorkOrder")
    void delete_RemovesWorkOrder() {
        when(workOrderRepository.findById(eq(1L))).thenReturn(Optional.of(workOrder));
        doNothing().when(workOrderRepository).delete(workOrder);

        workOrderService.delete(1L);

        verify(workOrderRepository, times(1)).findById(1L);
        verify(workOrderRepository, times(1)).delete(workOrder);
    }

    @Test
    @DisplayName("delete should throw EntityNotFoundException when not found")
    void delete_ThrowsEntityNotFoundException() {
        when(workOrderRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> workOrderService.delete(1L));
        verify(workOrderRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("update should throw ObjectOptimisticLockingFailureException on optimistic lock failure")
    void update_ThrowsOptimisticLockException() {
        when(workOrderRepository.findById(eq(1L))).thenReturn(Optional.of(workOrder));
        when(workOrderRepository.save(any(WorkOrder.class)))
                .thenThrow(ObjectOptimisticLockingFailureException.class);

        assertThrows(ObjectOptimisticLockingFailureException.class, () -> workOrderService.update(1L, workOrderRequest));
        verify(workOrderRepository, times(1)).findById(1L);
        verify(workOrderRepository, times(1)).save(workOrder);
    }

}
