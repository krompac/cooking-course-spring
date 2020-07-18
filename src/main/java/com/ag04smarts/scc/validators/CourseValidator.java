package com.ag04smarts.scc.validators;

import com.ag04smarts.scc.dto.CourseDto;
import com.ag04smarts.scc.dto.LecturerDto;
import com.ag04smarts.scc.model.enums.CourseType;
import com.ag04smarts.scc.services.CourseService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.Set;

@Component
public class CourseValidator implements Validator {
    private final CourseService courseService;

    public CourseValidator(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CourseDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CourseDto courseDto = (CourseDto) target;

        Long id = courseDto.getId();
        validateId(id, errors);

        String name = courseDto.getName();
        validateName(name, errors);

        String type = courseDto.getType();
        validateType(type, errors);

        Integer numberOfStudents = courseDto.getNumberOfStudents();
        validateNumberOfStudents(numberOfStudents, errors);

        Set<LecturerDto> lecturerDtos = courseDto.getLecturers();
        validateLecturers(lecturerDtos, errors);
    }

    private void validateId(Long id, Errors errors) {
        if (id != null && !courseService.existsById(id)) {
            errors.rejectValue("id", ErrorCodes.ERROR_NOT_FOUND, "Course doesn't exist");
        }
    }

    private void validateName(String name, Errors errors) {
        if (name == null || name.length() == 0) {
            errors.rejectValue("name", ErrorCodes.ERROR_COURSE_NAME_NULL, "Course name must be entered");
            return;
        }

        if (name.length() < 3) {
            errors.rejectValue("name", ErrorCodes.ERROR_COURSE_NAME_SHORT,
                    "Course cannot be shorter than 3 characters");
        } else if (name.length() > 50) {
            errors.rejectValue("name", ErrorCodes.ERROR_COURSE_NAME_LONG,
                    "Course cannot be longer than 50 characters");
        }
    }

    private void validateType(String type, Errors errors) {
        if (type == null) {
            errors.rejectValue("type", ErrorCodes.ERROR_COURSE_TYPE_NULL, "Course type must be entered");
            return;
        }

        if (Arrays.stream(CourseType.values()).noneMatch(value -> value.name().equals(type))) {
            errors.rejectValue("type", ErrorCodes.ERROR_NOT_FOUND, "Invalid course type");
        }
    }

    private void validateNumberOfStudents(Integer numberOfStudents, Errors errors) {
        if (numberOfStudents == null) {
            errors.rejectValue("numberOfStudents", ErrorCodes.ERROR_NUMBER_OF_STUDENTS_NULL,
                    "Enter number of students");
            return;
        }

        if (numberOfStudents < 1) {
            errors.rejectValue("numberOfStudents", ErrorCodes.ERROR_NUMBER_OF_STUDENTS_LESS_THAN_ONE,
                    "Course needs to have at least one spot");
        }
    }

    private void validateLecturers(Set<LecturerDto> lecturerDtos, Errors errors) {
        if (lecturerDtos == null || lecturerDtos.size() == 0) {
            errors.rejectValue("lecturers", ErrorCodes.ERROR_LECTURERS_NOT_ADDED,
                    "Lecturers need to be added to the course");
        }
    }
}
