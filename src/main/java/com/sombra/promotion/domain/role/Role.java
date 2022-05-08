package com.sombra.promotion.domain.role;

import com.sombra.promotion.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role extends BaseEntity {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "VARCHAR(50)", unique = true, nullable = false)
    private RoleType type;

    enum RoleType {
        STUDENT, INSTRUCTOR, ADMIN
    }

}
