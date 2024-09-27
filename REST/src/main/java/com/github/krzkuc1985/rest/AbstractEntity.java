package com.github.krzkuc1985.rest;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Setter
    private Long version;
}
