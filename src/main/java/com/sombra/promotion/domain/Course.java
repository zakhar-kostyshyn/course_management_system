package com.sombra.promotion.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course")
public class Course extends BaseEntity {

    @NotNull
    @Column(name = "name", columnDefinition = "VARCHAR(255)", unique = true, nullable = false)
    private String name;

}
