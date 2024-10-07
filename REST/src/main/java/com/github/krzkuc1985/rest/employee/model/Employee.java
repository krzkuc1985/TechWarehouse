package com.github.krzkuc1985.rest.employee.model;

import com.github.krzkuc1985.rest.AbstractEntity;
import com.github.krzkuc1985.rest.address.Address;
import com.github.krzkuc1985.rest.logindata.LoginData;
import com.github.krzkuc1985.rest.personaldata.PersonalData;
import com.github.krzkuc1985.rest.role.model.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Employee extends AbstractEntity {

    @Embedded
    private PersonalData personalData;

    @Embedded
    private Address address;

    @Embedded
    private LoginData loginData;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;
}
