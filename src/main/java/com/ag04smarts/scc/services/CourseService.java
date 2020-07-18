package com.ag04smarts.scc.services;

import com.ag04smarts.scc.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    Course save(Course course);

    void saveAll(Iterable<Course> courses);

    List<Course> findAll();

    void delete(Course course);

    List<Course> findAllByLecturersId(Long id);

    Optional<Course> findById(Long id);

    boolean existsById(Long id);
}
