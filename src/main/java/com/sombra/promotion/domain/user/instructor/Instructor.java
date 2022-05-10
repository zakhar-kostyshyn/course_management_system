package com.sombra.promotion.domain.user.instructor;

import com.sombra.promotion.domain.course.Course;
import com.sombra.promotion.domain.user.User;
import com.sombra.promotion.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "instructor")
public class Instructor extends User {

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "instructors")
    private Set<Course> courses;

    public Role getRole() {
        return Role.INSTRUCTOR;
    }
}
