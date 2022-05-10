package com.sombra.promotion.domain.course;

import com.sombra.promotion.domain.BaseEntity;
import com.sombra.promotion.domain.lesson.Lesson;
import com.sombra.promotion.domain.user.instructor.Instructor;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course")
public class Course extends BaseEntity {

    @NotNull
    @Column(name = "name", columnDefinition = "VARCHAR(255)", unique = true, nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Lesson> lessons;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "course_instructor",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "instructor_id")
    )
    private Set<Instructor> instructors;

}
