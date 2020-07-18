package com.ag04smarts.scc.converters;

import com.ag04smarts.scc.dto.LecturerDto;
import com.ag04smarts.scc.model.Lecturer;
import com.ag04smarts.scc.services.CourseService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LecturerDtoToLecturer implements Converter<LecturerDto, Lecturer> {
    private final CourseService courseService;

    public LecturerDtoToLecturer(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public Lecturer convert(LecturerDto source) {
        if (source == null) {
            return null;
        }

        final Lecturer lecturer = new Lecturer();

        lecturer.setId(source.getId());
        lecturer.setFirstName(source.getFirstName());
        lecturer.setLastName(source.getLastName());

        if (source.getCourses() != null) {
            source.getCourses().forEach(courseId -> {
                if (courseId != null) {
                    this.courseService.findById(courseId).ifPresent(lecturer::addCourse);
                }
            });
        }

        return lecturer;
    }
}
