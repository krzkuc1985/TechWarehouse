package com.github.krzkuc1985.rest.workorderstatus.repository;

import com.github.krzkuc1985.rest.workorderstatus.model.WorkOrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class WorkOrderStatusRepositoryTest {

    @Autowired
    private WorkOrderStatusRepository workOrderStatusRepository;

    private WorkOrderStatus workOrderStatus;

    @BeforeEach
    void setUp() {
        workOrderStatus = new WorkOrderStatus("New_Test");
    }

    @Test
    @DisplayName("should save and retrieve WorkOrderStatus")
    void saveAndFindById() {
        // when
        WorkOrderStatus savedWorkOrderStatus = workOrderStatusRepository.save(workOrderStatus);
        Optional<WorkOrderStatus> foundUnit = workOrderStatusRepository.findById(savedWorkOrderStatus.getId());

        // then
        assertTrue(foundUnit.isPresent());
        assertEquals("New_Test", foundUnit.get().getStatus());
    }

    @Test
    @DisplayName("should delete WorkOrderStatus")
    void deleteWorkOrderStatus() {
        // given
        WorkOrderStatus savedWorkOrderStatus = workOrderStatusRepository.save(workOrderStatus);

        // when
        workOrderStatusRepository.deleteById(savedWorkOrderStatus.getId());
        Optional<WorkOrderStatus> foundUnit = workOrderStatusRepository.findById(savedWorkOrderStatus.getId());

        // then
        assertFalse(foundUnit.isPresent());
    }

    @Test
    @DisplayName("should update WorkOrderStatus symbol")
    void updateWorkOrderStatusSymbol() {
        // given
        WorkOrderStatus savedWorkOrderStatus = workOrderStatusRepository.save(workOrderStatus);

        // when
        savedWorkOrderStatus.setStatus("Completed_Test");
        workOrderStatusRepository.save(savedWorkOrderStatus);
        Optional<WorkOrderStatus> updatedUnit = workOrderStatusRepository.findById(savedWorkOrderStatus.getId());

        // then
        assertTrue(updatedUnit.isPresent());
        assertEquals("Completed_Test", updatedUnit.get().getStatus());
    }

    @Test
    @DisplayName("should return empty when WorkOrderStatus not found")
    void findById_NotFound() {
        // when
        Optional<WorkOrderStatus> foundUnit = workOrderStatusRepository.findById(999L);

        // then
        assertTrue(foundUnit.isEmpty());
    }

}
