package com.ag04smarts.scc.facade.impl;

import com.ag04smarts.scc.dto.CourseDto;
import com.ag04smarts.scc.converters.CourseDtoToCourse;
import com.ag04smarts.scc.converters.CourseToCourseDto;
import com.ag04smarts.scc.facade.CourseFacade;
import com.ag04smarts.scc.model.Course;
import com.ag04smarts.scc.model.Registration;
import com.ag04smarts.scc.services.CourseService;
import com.ag04smarts.scc.services.RegistrationService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Component
public class CourseFacadeImpl implements CourseFacade {
    private final CourseService courseService;
    private final RegistrationService registrationService;
    private final CourseToCourseDto courseToCourseDto;
    private final CourseDtoToCourse courseDtoToCourse;

    public CourseFacadeImpl(CourseService courseService, RegistrationService registrationService,
                            CourseToCourseDto courseToCourseDto, CourseDtoToCourse courseDtoToCourse) {
        this.courseService = courseService;
        this.registrationService = registrationService;
        this.courseToCourseDto = courseToCourseDto;
        this.courseDtoToCourse = courseDtoToCourse;
    }

    @Override
    public Optional<Course> findById(Long id) {
        return courseService.findById(id);
    }

    @Override
    public HashSet<CourseDto> findAll() {
        HashSet<CourseDto> courseDtos = new HashSet<>();
        courseService.findAll().stream().forEach(course -> {
            courseDtos.add(courseToCourseDto.convert(course));
        });

        return courseDtos;
    }

    @Override
    public CourseDto save(CourseDto courseDto) {
        Course courseToAdd = courseDtoToCourse.convert(courseDto);
        Course savedCourse = courseService.save(courseToAdd);
        courseDto.setId(savedCourse.getId());

        return courseDto;
    }

    @Override
    public CourseDto update(CourseDto courseDto) {
        Course updatedCourse = courseDtoToCourse.convert(courseDto);
        return courseToCourseDto.convert(courseService.save(updatedCourse));
    }

    @Override
    public void removeCourseFromRegistrations(Course course, List<Long> registrationIds) {
        List<Registration> registrations = registrationService.findAllWithId(registrationIds);
        registrations.stream().forEach(registration -> registration.removeCourse(course));

        course.setNumberOfStudents(course.getNumberOfStudents() + 1);
        courseService.save(course);
    }

    @Override
    public boolean delete(Course course) {
        if (course == null) {
            return false;
        }
        registrationService.findAllRegistrationsByCourseId(course.getId()).forEach(registration -> {
            registration.removeCourse(course);
        });

        courseService.delete(course);

        return true;
    }
}
