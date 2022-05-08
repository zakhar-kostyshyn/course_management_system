package com.sombra.promotion.domain.lesson;

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
@Table(name = "lesson")
public class Lesson extends BaseEntity {

    @NotNull
    @Column(name = "name", columnDefinition = "VARCHAR(255)", unique = true, nullable = false)
    private String name;



}
