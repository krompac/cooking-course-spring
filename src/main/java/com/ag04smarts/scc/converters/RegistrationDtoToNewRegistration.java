package com.ag04smarts.scc.converters;

import com.ag04smarts.scc.dto.CandidateDto;
import com.ag04smarts.scc.dto.RegistrationDto;
import com.ag04smarts.scc.model.Candidate;
import com.ag04smarts.scc.model.Registration;
import com.ag04smarts.scc.services.CourseService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RegistrationDtoToNewRegistration implements Converter<RegistrationDto, Registration> {
    private final CandidateDtoToCandidate commandToCandidate;
    private final CourseService courseService;

    public RegistrationDtoToNewRegistration(CandidateDtoToCandidate commandToCandidate, CourseService courseService) {
        this.commandToCandidate = commandToCandidate;
        this.courseService = courseService;
    }

    @Nullable
    @Override
    public Registration convert(RegistrationDto source) {
        Registration registration = getRegistration(source);

        if (registration != null && source.getCourses() != null && source.getCourses().size() > 0) {
            source.getCourses().forEach(courseId -> courseService.findById(courseId).ifPresent(registration::addCourse));
        }

        return registration;
    }

    public Registration getRegistration(RegistrationDto registrationDto) {
        if (registrationDto == null) {
            return null;
        }

        Registration registration = new Registration();
        registration.setId(registrationDto.getId());
        registration.setRegistrationDate(registrationDto.getRegistrationDate());

        CandidateDto candidate = registrationDto.getCandidate();
        if (candidate != null) {
            Candidate convertedCandidate = commandToCandidate.convert(candidate);

            if (convertedCandidate != null) {
                registration.addCandidate(convertedCandidate);
            }
        }

        return registration;
    }
}
