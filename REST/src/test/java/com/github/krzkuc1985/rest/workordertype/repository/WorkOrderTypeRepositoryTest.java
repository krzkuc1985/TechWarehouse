package com.github.krzkuc1985.rest.workordertype.repository;

import com.github.krzkuc1985.rest.workordertype.model.WorkOrderType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class WorkOrderTypeRepositoryTest {

    @Autowired
    private WorkOrderTypeRepository workOrderTypeRepository;

    private WorkOrderType workOrderType;

    @BeforeEach
    void setUp() {
        workOrderType = new WorkOrderType("Repair_Test");
    }

    @Test
    @DisplayName("should save and retrieve WorkOrderType")
    void saveAndFindById() {
        // when
        WorkOrderType savedWorkOrderType = workOrderTypeRepository.save(workOrderType);
        Optional<WorkOrderType> foundUnit = workOrderTypeRepository.findById(savedWorkOrderType.getId());

        // then
        assertTrue(foundUnit.isPresent());
        assertEquals("Repair_Test", foundUnit.get().getType());
    }

    @Test
    @DisplayName("should delete WorkOrderType")
    void deleteWorkOrderType() {
        // given
        WorkOrderType savedWorkOrderType = workOrderTypeRepository.save(workOrderType);

        // when
        workOrderTypeRepository.deleteById(savedWorkOrderType.getId());
        Optional<WorkOrderType> foundUnit = workOrderTypeRepository.findById(savedWorkOrderType.getId());

        // then
        assertFalse(foundUnit.isPresent());
    }

    @Test
    @DisplayName("should update WorkOrderType type")
    void updateWorkOrderTypeSymbol() {
        // given
        WorkOrderType savedWorkOrderType = workOrderTypeRepository.save(workOrderType);

        // when
        savedWorkOrderType.setType("Modernization_Test");
        workOrderTypeRepository.save(savedWorkOrderType);
        Optional<WorkOrderType> updatedUnit = workOrderTypeRepository.findById(savedWorkOrderType.getId());

        // then
        assertTrue(updatedUnit.isPresent());
        assertEquals("Modernization_Test", updatedUnit.get().getType());
    }

    @Test
    @DisplayName("should return empty when WorkOrderType not found")
    void findById_NotFound() {
        // when
        Optional<WorkOrderType> foundUnit = workOrderTypeRepository.findById(999L);

        // then
        assertTrue(foundUnit.isEmpty());
    }

}