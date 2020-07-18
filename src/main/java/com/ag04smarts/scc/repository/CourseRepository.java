package com.ag04smarts.scc.repository;

import com.ag04smarts.scc.model.Course;
import com.ag04smarts.scc.model.enums.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findCourseByType(CourseType type);
    List<Course> findAllByLecturersId(Long id);
}
