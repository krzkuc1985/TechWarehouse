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
        WorkOrderType saved = workOrderTypeRepository.save(workOrderType);
        Optional<WorkOrderType> found = workOrderTypeRepository.findById(saved.getId());

        // then
        assertTrue(found.isPresent());
        assertEquals("Repair_Test", found.get().getType());
    }

    @Test
    @DisplayName("should delete WorkOrderType")
    void deleteWorkOrderType() {
        // given
        WorkOrderType saved = workOrderTypeRepository.save(workOrderType);

        // when
        workOrderTypeRepository.deleteById(saved.getId());
        Optional<WorkOrderType> found = workOrderTypeRepository.findById(saved.getId());

        // then
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("should update WorkOrderType type")
    void updateWorkOrderType() {
        // given
        WorkOrderType saved = workOrderTypeRepository.save(workOrderType);

        // when
        saved.setType("Modernization_Test");
        workOrderTypeRepository.save(saved);
        Optional<WorkOrderType> updated = workOrderTypeRepository.findById(saved.getId());

        // then
        assertTrue(updated.isPresent());
        assertEquals("Modernization_Test", updated.get().getType());
    }

    @Test
    @DisplayName("should return empty when WorkOrderType not found")
    void findById_NotFound() {
        // when
        Optional<WorkOrderType> found = workOrderTypeRepository.findById(999L);

        // then
        assertTrue(found.isEmpty());
    }

}