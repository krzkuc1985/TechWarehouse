package com.github.krzkuc1985.rest.role.model;

import com.github.krzkuc1985.rest.AbstractEntity;
import com.github.krzkuc1985.rest.permission.model.Permission;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Role extends AbstractEntity {

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;
}
