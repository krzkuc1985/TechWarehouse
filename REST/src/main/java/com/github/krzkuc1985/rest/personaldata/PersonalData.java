package com.github.krzkuc1985.rest.personaldata;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Embeddable
public class PersonalData {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Column(unique = true)
    private String phoneNumber;

    @Email
    @Column(unique = true)
    private String email;
}
