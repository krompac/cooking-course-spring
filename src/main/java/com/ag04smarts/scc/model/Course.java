package com.ag04smarts.scc.model;
import com.ag04smarts.scc.model.enums.CourseType;

import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.*;
import javax.validation.constraints.Max;

@Data
@Entity
@EqualsAndHashCode(exclude = {"lecturers", "registrations"})
@ToString(exclude = {"lecturers", "registrations"})
@Table(name = "Course")
public class Course extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private CourseType type;

    @Column(name = "numberOfStudents")
    private Integer numberOfStudents = 0;

    @ManyToMany
    @JoinTable(name = "courseLecturers", joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "lecturer_id"))
    private Set<Lecturer> lecturers = new HashSet<>();

    @ManyToMany(mappedBy = "courses")
    private List<Registration> registrations = new ArrayList<>();

    public void addLecturer(Lecturer lecturer){
        lecturer.getCourses().add(this);
        this.lecturers.add(lecturer);
    }
}
