package com.github.krzkuc1985.rest.workorder.repository;

import com.github.krzkuc1985.rest.address.Address;
import com.github.krzkuc1985.rest.employee.model.Employee;
import com.github.krzkuc1985.rest.inventory.model.Inventory;
import com.github.krzkuc1985.rest.logindata.LoginData;
import com.github.krzkuc1985.rest.personaldata.PersonalData;
import com.github.krzkuc1985.rest.role.model.Role;
import com.github.krzkuc1985.rest.workorder.model.WorkOrder;
import com.github.krzkuc1985.rest.workorderstatus.model.WorkOrderStatus;
import com.github.krzkuc1985.rest.workordertype.model.WorkOrderType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class WorkOrderRepositoryTest {

    @Autowired
    private WorkOrderRepository workOrderRepository;

    private WorkOrder workOrder;

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
    }

    @Test
    @DisplayName("should save and retrieve WorkOrder")
    void saveAndFindById() {
        // when
        WorkOrder saved = workOrderRepository.save(workOrder);
        Optional<WorkOrder> found = workOrderRepository.findById(saved.getId());

        // then
        assertTrue(found.isPresent());
        assertEquals("Replacing a faulty sensor", found.get().getDescription());
    }

    @Test
    @DisplayName("should delete WorkOrder")
    void deleteWorkOrder() {
        // given
        WorkOrder saved = workOrderRepository.save(workOrder);

        // when
        workOrderRepository.deleteById(saved.getId());
        Optional<WorkOrder> found = workOrderRepository.findById(saved.getId());

        // then
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("should update WorkOrder")
    void updateWorkOrder() {
        // given
        WorkOrder saved = workOrderRepository.save(workOrder);

        // when
        saved.setDescription("Replacing a faulty power supply");
        workOrderRepository.save(saved);
        Optional<WorkOrder> updated = workOrderRepository.findById(saved.getId());

        // then
        assertTrue(updated.isPresent());
        assertEquals("Replacing a faulty power supply", updated.get().getDescription());
    }

    @Test
    @DisplayName("should return empty when WorkOrder not found")
    void findById_NotFound() {
        // when
        Optional<WorkOrder> found = workOrderRepository.findById(999L);

        // then
        assertTrue(found.isEmpty());
    }

}
