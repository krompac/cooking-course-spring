package com.ag04smarts.scc.services.impl;

import com.ag04smarts.scc.model.Course;
import com.ag04smarts.scc.repository.CourseRepository;
import com.ag04smarts.scc.services.CourseService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void saveAll(Iterable<Course> courses) {
        courseRepository.saveAll(courses);
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return courseRepository.existsById(id);
    }

    @Override
    public void delete(Course course) {
        courseRepository.delete(course);
    }

    @Override
    public List<Course> findAllByLecturersId(Long id) {
        return courseRepository.findAllByLecturersId(id);
    }

    @Override
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }
}
