package com.sombra.promotion.domain.user;

import com.sombra.promotion.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
abstract public class User extends BaseEntity {

    @NotNull
    @Column(name = "username", columnDefinition = "VARCHAR(255)", unique = true, nullable = false)
    private String username;

    @NotNull
    @Column(name = "password", columnDefinition = "VARCHAR(255)", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    protected abstract Role getRole();

}
