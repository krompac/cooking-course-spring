package com.ag04smarts.scc.converters;

import com.ag04smarts.scc.dto.CourseDto;
import com.ag04smarts.scc.model.Course;
import com.ag04smarts.scc.model.enums.CourseType;
import com.ag04smarts.scc.services.RegistrationService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CourseDtoToCourse implements Converter<CourseDto, Course> {
    private RegistrationService registrationService;
    private LecturerDtoToLecturer lecturerDtoToLecturer;

    public CourseDtoToCourse(RegistrationService registrationService, LecturerDtoToLecturer lecturerDtoToLecturer) {
        this.registrationService = registrationService;
        this.lecturerDtoToLecturer = lecturerDtoToLecturer;
    }

    @Override
    public Course convert(CourseDto source) {
        if (source == null) {
            return null;
        }

        final Course course = new Course();
        course.setId(source.getId());
        course.setName(source.getName());
        course.setNumberOfStudents(source.getNumberOfStudents());
        course.setType(CourseType.valueOf(source.getType()));

        if (source.getLecturers().size() > 0) {
            source.getLecturers().stream().forEach(lecturerCommand -> {
                course.getLecturers().add(lecturerDtoToLecturer.convert(lecturerCommand));
            });
        }

        if (source.getRegistrations().size() > 0) {
            source.getRegistrations().stream().forEach(registrationCommand -> {
                course.getRegistrations().add(registrationService.findRegistrationById(registrationCommand.getId()));
            });
        }

        return course;
    }
}
