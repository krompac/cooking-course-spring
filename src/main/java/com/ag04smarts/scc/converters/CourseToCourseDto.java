package com.ag04smarts.scc.converters;

import com.ag04smarts.scc.model.Course;
import com.ag04smarts.scc.dto.CourseDto;
import com.ag04smarts.scc.services.RegistrationService;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

@Component
public class CourseToCourseDto implements Converter<Course, CourseDto> {
    private final LecturerToLecturerDto lecturerToLecturerDto;
    private final RegistrationToRegistrationDto registrationToRegistrationDto;

    public CourseToCourseDto(LecturerToLecturerDto lecturerToLecturerDto,
                             RegistrationToRegistrationDto registrationToRegistrationDto) {
        this.lecturerToLecturerDto = lecturerToLecturerDto;
        this.registrationToRegistrationDto = registrationToRegistrationDto;
    }

    @Override
    public CourseDto convert(Course source) {
        if (source == null) {
            return null;
        }

        final CourseDto courseDto = new CourseDto();

        courseDto.setId(source.getId());
        courseDto.setName(source.getName());
        courseDto.setNumberOfStudents(source.getNumberOfStudents());
        courseDto.setType(source.getType().name());

        source.getLecturers().forEach(lecturer -> {
            courseDto.getLecturers().add(lecturerToLecturerDto.convert(lecturer));
        });

        source.getRegistrations().forEach(registration -> {
          courseDto.getRegistrations().add(registrationToRegistrationDto.convert(registration));
        });

        return courseDto;
    }
}
