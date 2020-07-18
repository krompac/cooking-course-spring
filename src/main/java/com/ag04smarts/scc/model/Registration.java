package com.ag04smarts.scc.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"courses", "candidate"})
@ToString(exclude = {"courses", "candidate"})
@Entity
@Table(name = "Registration")
public class Registration extends BaseEntity {
    @Column(name = "registrationDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationDate;

    @Column(name = "courses")
    @ManyToMany
    @JoinTable(name = "registeredCourses", joinColumns = @JoinColumn(name = "registration_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> courses = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    private Candidate candidate;

    public void addCandidate(Candidate candidate){
        this.candidate = candidate;
        candidate.setRegistration(this);
    }

    public void addCourse(Course course){
        this.courses.add(course);
        int numberOfStudents = course.getNumberOfStudents() - 1;
        course.setNumberOfStudents(numberOfStudents);
        course.getRegistrations().add(this);
    }

    public void addExistingCourse(Course course) {
        this.courses.add(course);
        course.getRegistrations().add(this);
    }

    public void removeCourse(Course course) {
        this.courses.remove(course);
        course.getRegistrations().remove(this);
    }
}
