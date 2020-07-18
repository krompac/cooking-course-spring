package com.ag04smarts.scc.facade;

import com.ag04smarts.scc.dto.CourseDto;
import com.ag04smarts.scc.model.Course;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public interface CourseFacade {

    Optional<Course> findById(Long id);

    HashSet<CourseDto> findAll();

    CourseDto save(CourseDto courseDto);

    CourseDto update(CourseDto courseDto);

    void removeCourseFromRegistrations(Course course, List<Long> registrationIds);

    boolean delete(Course course);
}
