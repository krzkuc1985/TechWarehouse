package com.github.krzkuc1985.rest.permission.repository;

import com.github.krzkuc1985.rest.permission.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByName(String name);

}
