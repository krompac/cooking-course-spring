package com.ag04smarts.scc.validators;

import com.ag04smarts.scc.dto.LecturerDto;
import com.ag04smarts.scc.services.LecturerService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class LecturerValidator implements Validator {
    private final LecturerService lecturerService;

    public LecturerValidator(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return LecturerDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LecturerDto lecturerDto = (LecturerDto) target;

        Long id = lecturerDto.getId();
        validateId(id, errors);

        String firstName = lecturerDto.getFirstName();
        validateFirstName(firstName, errors);

        String lastName = lecturerDto.getLastName();
        validateLastName(lastName, errors);

    }

    private void validateId(Long id, Errors errors) {
        if (id != null && !lecturerService.existsById(id)) {
            errors.rejectValue("id", ErrorCodes.ERROR_NOT_FOUND, "Mentor with that id does not exist");
        }
    }

    private void validateFirstName(String firstName, Errors errors) {
        if (firstName == null || firstName.length() == 0) {
            errors.rejectValue("firstName", ErrorCodes.ERROR_FIRST_NAME_NULL, "First name must be entered");
            return;
        }

        if (firstName.length() > 25) {
            errors.rejectValue("firstName", ErrorCodes.ERROR_FIRST_NAME_LONG,
                    "First name cannot be longer than 25 characters");
        }

        if (firstName.length() < 3) {
            errors.rejectValue("firstName", ErrorCodes.ERROR_FIRST_NAME_SHORT,
                    "First name must be at least 3 characters long");
        }
    }

    private void validateLastName(String lastName, Errors errors) {
        if (lastName == null || lastName.length() == 0) {
            errors.rejectValue("lastName", ErrorCodes.ERROR_LAST_NAME_NULL, "Last name must be entered");
            return;
        }

        if (lastName.length() > 40) {
            errors.rejectValue("lastName", ErrorCodes.ERROR_LAST_NAME_LONG,
                    "First name cannot be longer than 40 characters");
        }

        if (lastName.length() < 3) {
            errors.rejectValue("lastName", ErrorCodes.ERROR_LAST_NAME_SHORT,
                    "Last name must be at least 3 characters long");
        }
    }
}
