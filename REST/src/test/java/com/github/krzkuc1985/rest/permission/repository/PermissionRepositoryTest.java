package com.github.krzkuc1985.rest.permission.repository;

import com.github.krzkuc1985.rest.permission.model.Permission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PermissionRepositoryTest {

    @Autowired
    private PermissionRepository permissionRepository;

    private Permission permission;

    @BeforeEach
    void setUp() {
        permission = new Permission("WRITE_PERMISSION", "PERMISSION");
    }

    @Test
    @DisplayName("should save and retrieve Permission by ID")
    void saveAndFindById() {
        // when
        Permission savedPermission = permissionRepository.save(permission);
        Optional<Permission> foundPermission = permissionRepository.findById(savedPermission.getId());

        // then
        assertTrue(foundPermission.isPresent());
        assertEquals("WRITE_PERMISSION", foundPermission.get().getName());
    }

    @Test
    @DisplayName("should find Permission by name")
    void findByName() {
        // given
        permissionRepository.save(permission);

        // when
        Optional<Permission> foundPermission = permissionRepository.findByName("WRITE_PERMISSION");

        // then
        assertTrue(foundPermission.isPresent());
        assertEquals("WRITE_PERMISSION", foundPermission.get().getName());
    }

    @Test
    @DisplayName("should delete Permission")
    void deletePermission() {
        // given
        Permission savedPermission = permissionRepository.save(permission);

        // when
        permissionRepository.deleteById(savedPermission.getId());
        Optional<Permission> foundPermission = permissionRepository.findById(savedPermission.getId());

        // then
        assertFalse(foundPermission.isPresent());
    }

    @Test
    @DisplayName("should return empty when Permission not found by ID")
    void findById_NotFound() {
        // when
        Optional<Permission> foundPermission = permissionRepository.findById(999L);

        // then
        assertTrue(foundPermission.isEmpty());
    }

    @Test
    @DisplayName("should return empty when Permission not found by name")
    void findByName_NotFound() {
        // when
        Optional<Permission> foundPermission = permissionRepository.findByName("NON_EXISTENT");

        // then
        assertTrue(foundPermission.isEmpty());
    }
}