package com.github.krzkuc1985.rest.role.repository;

import com.github.krzkuc1985.rest.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

}
