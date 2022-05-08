package com.sombra.promotion.domain.user;

import com.sombra.promotion.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User extends BaseEntity {

    @NotNull
    @Column(name = "username", columnDefinition = "VARCHAR(255)", unique = true, nullable = false)
    private String username;

    @NotNull
    @Column(name = "password", columnDefinition = "VARCHAR(255)", nullable = false)
    private String password;

}
