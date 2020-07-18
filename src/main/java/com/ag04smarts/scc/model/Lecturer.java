package com.ag04smarts.scc.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@ToString(exclude = {"courses"})
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Lecturer")
public class Lecturer extends Person {
    @ManyToMany(mappedBy = "lecturers")
    private Set<Course> courses = new HashSet<>();

    public void addCourse(Course course) {
        course.getLecturers().add(this);
        courses.add(course);
    }
}
