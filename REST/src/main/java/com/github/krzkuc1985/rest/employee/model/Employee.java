package com.github.krzkuc1985.rest.employee.model;

import com.github.krzkuc1985.rest.AbstractEntity;
import com.github.krzkuc1985.rest.address.Address;
import com.github.krzkuc1985.rest.logindata.LoginData;
import com.github.krzkuc1985.rest.personaldata.PersonalData;
import com.github.krzkuc1985.rest.role.model.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Employee extends AbstractEntity implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return loginData.getPassword();
    }

    @Override
    public String getUsername() {
        return loginData.getLogin();
    }

}
