package com.ag04smarts.scc.converters;

import com.ag04smarts.scc.dto.RegistrationDto;
import com.ag04smarts.scc.model.Registration;
import com.ag04smarts.scc.services.CourseService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RegistrationDtoToExistingRegistration implements Converter<RegistrationDto, Registration> {
    private final CourseService courseService;
    private final RegistrationDtoToNewRegistration registrationDtoToNewRegistration;

    public RegistrationDtoToExistingRegistration(CourseService courseService,
                                                 RegistrationDtoToNewRegistration registrationDtoToNewRegistration) {
        this.courseService = courseService;
        this.registrationDtoToNewRegistration = registrationDtoToNewRegistration;
    }

    @Nullable
    @Override
    public Registration convert(RegistrationDto source) {
        Registration registration = registrationDtoToNewRegistration.getRegistration(source);
        registration.setId(source.getId());

        if (source.getCourses() != null && source.getCourses().size() > 0) {
            source.getCourses().forEach(courseId -> courseService.findById(courseId).ifPresent(registration::addExistingCourse));
        }

        return registration;
    }
}

