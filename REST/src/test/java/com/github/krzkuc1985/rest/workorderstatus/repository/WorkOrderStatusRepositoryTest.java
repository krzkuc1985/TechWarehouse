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
        WorkOrderStatus saved = workOrderStatusRepository.save(workOrderStatus);
        Optional<WorkOrderStatus> found = workOrderStatusRepository.findById(saved.getId());

        // then
        assertTrue(found.isPresent());
        assertEquals("New_Test", found.get().getStatus());
    }

    @Test
    @DisplayName("should delete WorkOrderStatus")
    void deleteWorkOrderStatus() {
        // given
        WorkOrderStatus saved = workOrderStatusRepository.save(workOrderStatus);

        // when
        workOrderStatusRepository.deleteById(saved.getId());
        Optional<WorkOrderStatus> found = workOrderStatusRepository.findById(saved.getId());

        // then
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("should update WorkOrderStatus")
    void updateWorkOrderStatus() {
        // given
        WorkOrderStatus saved = workOrderStatusRepository.save(workOrderStatus);

        // when
        saved.setStatus("Completed_Test");
        workOrderStatusRepository.save(saved);
        Optional<WorkOrderStatus> updated = workOrderStatusRepository.findById(saved.getId());

        // then
        assertTrue(updated.isPresent());
        assertEquals("Completed_Test", updated.get().getStatus());
    }

    @Test
    @DisplayName("should return empty when WorkOrderStatus not found")
    void findById_NotFound() {
        // when
        Optional<WorkOrderStatus> found = workOrderStatusRepository.findById(999L);

        // then
        assertTrue(found.isEmpty());
    }

}
