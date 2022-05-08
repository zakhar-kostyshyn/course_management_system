package com.sombra.promotion.domain.homework;

import com.sombra.promotion.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "homework")
public class Homework extends BaseEntity {

    @NotNull
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "homework", columnDefinition = "CLOB", nullable = false)
    private String homework;

}
