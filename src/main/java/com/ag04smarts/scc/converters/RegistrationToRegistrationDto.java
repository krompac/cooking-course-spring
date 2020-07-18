package com.ag04smarts.scc.converters;

import com.ag04smarts.scc.dto.CandidateDto;
import com.ag04smarts.scc.dto.RegistrationDto;
import com.ag04smarts.scc.model.Candidate;
import com.ag04smarts.scc.model.Course;
import com.ag04smarts.scc.model.Registration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Set;

@Component
public class RegistrationToRegistrationDto implements Converter<Registration, RegistrationDto> {

    @Transactional
    @Override
    public RegistrationDto convert(Registration registration) {

        if (registration == null) {
            return null;
        }

        final RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setId(registration.getId());
        registrationDto.setRegistrationDate(registration.getRegistrationDate());

        final Set<Course> courses = registration.getCourses();

        if (courses != null && courses.size() > 0) {
            courses.forEach(course -> {
                registrationDto.getCourses().add(course.getId());
            });
        }

        final Candidate candidate = registration.getCandidate();

        if (candidate != null) {
            CandidateDto candidateDto = new CandidateDto();
            candidateDto.setId(candidate.getId());
            candidateDto.setFirstName(candidate.getFirstName());
            candidateDto.setLastName(candidate.getLastName());
            registrationDto.setCandidate(candidateDto);
        }

        return registrationDto;
    }
}
