package com.ag04smarts.scc.validators;

import com.ag04smarts.scc.dto.RegistrationDto;
import com.ag04smarts.scc.model.Course;
import com.ag04smarts.scc.services.CourseService;
import com.ag04smarts.scc.services.RegistrationService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.util.Set;

@Component
public class RegistrationValidator implements Validator {
    private final CourseService courseService;
    private final RegistrationService registrationService;

    public RegistrationValidator(CourseService courseService, RegistrationService registrationService) {
        this.courseService = courseService;
        this.registrationService = registrationService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegistrationDto registrationDto = (RegistrationDto) target;

        Long registrationId = registrationDto.getId();
        validateId(registrationId, errors);

        LocalDate registrationDate = registrationDto.getRegistrationDate();
        validateRegistrationDate(registrationDate, registrationId, errors);

        Set<Long> coursesIds = registrationDto.getCourses();
        validateCourses(coursesIds, errors);
    }

    private void validateId(Long registrationId, Errors errors) {
        if (registrationId != null && !registrationService.existsById(registrationId)) {
            errors.rejectValue("registration.id", ErrorCodes.ERROR_NOT_FOUND, "Registration not found");
        }
    }

    private void validateRegistrationDate(LocalDate registrationDate, Long registrationId, Errors errors) {
        if (registrationDate == null) {
            errors.rejectValue("registration.registrationDate", ErrorCodes.ERROR_REGISTRATION_DATE_NULL,
                    "Registration date must be entered");
            return;
        }

        if (registrationId == null) {
            LocalDate today = LocalDate.now();
            if (registrationDate.isBefore(today)) {
                errors.rejectValue("registration.registrationDate", ErrorCodes.ERROR_REGISTRATION_DATE_BEFORE_TODAY,
                        "Registration date cannot be before today");
            }
        }
    }

    private void validateCourses(Set<Long> coursesIds, Errors errors) {
        boolean nonExistingCourse = false;
        boolean courseAlreadyFull = false;

        for (Long courseId : coursesIds) {
            Course course = courseService.findById(courseId).orElse(null);

            if (course == null) {
                nonExistingCourse = true;
            } else if (course.getNumberOfStudents() == 0)  {
                courseAlreadyFull = true;
            }

            if (nonExistingCourse && courseAlreadyFull) {
                break;
            }
        }

        if (nonExistingCourse) {
            errors.rejectValue("registration.courses", ErrorCodes.ERROR_COURSE_NULL, "Course doesn't exist");
        }

        if (courseAlreadyFull) {
            errors.rejectValue("registration.courses", ErrorCodes.ERROR_COURSE_ALREADY_FULL, "Course is already full");
        }
    }
}


