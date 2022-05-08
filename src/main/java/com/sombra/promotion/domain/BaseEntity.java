package com.sombra.promotion.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            columnDefinition = "INT",
            nullable = false,
            unique = true,
            insertable = false
    )
    private int id;

    @Column(name = "deleted", columnDefinition = "BIT")
    private boolean deleted;

    @CreationTimestamp
    @Column(name = "created", columnDefinition = "DATETIME")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated", columnDefinition = "DATETIME")
    private LocalDateTime updated;


}
