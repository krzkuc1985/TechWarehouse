package com.github.krzkuc1985.rest.role.repository;

import com.github.krzkuc1985.rest.permission.model.Permission;
import com.github.krzkuc1985.rest.role.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role("ADMIN", new HashSet<Permission>());
    }

    @Test
    @DisplayName("should save and retrieve Role by ID")
    void saveAndFindById() {
        // when
        Role savedRole = roleRepository.save(role);
        Optional<Role> foundRole = roleRepository.findById(savedRole.getId());

        // then
        assertTrue(foundRole.isPresent());
        assertEquals("ADMIN", foundRole.get().getName());
    }

    @Test
    @DisplayName("should find Role by name")
    void findByName() {
        // given
        roleRepository.save(role);

        // when
        Optional<Role> foundRole = roleRepository.findByName("ADMIN");

        // then
        assertTrue(foundRole.isPresent());
        assertEquals("ADMIN", foundRole.get().getName());
    }

    @Test
    @DisplayName("should delete Role")
    void deleteRole() {
        // given
        Role savedRole = roleRepository.save(role);

        // when
        roleRepository.deleteById(savedRole.getId());
        Optional<Role> foundRole = roleRepository.findById(savedRole.getId());

        // then
        assertFalse(foundRole.isPresent());
    }

    @Test
    @DisplayName("should return empty when Role not found by ID")
    void findById_NotFound() {
        // when
        Optional<Role> foundRole = roleRepository.findById(999L);

        // then
        assertTrue(foundRole.isEmpty());
    }

    @Test
    @DisplayName("should return empty when Role not found by name")
    void findByName_NotFound() {
        // when
        Optional<Role> foundRole = roleRepository.findByName("NON_EXISTENT");

        // then
        assertTrue(foundRole.isEmpty());
    }
}