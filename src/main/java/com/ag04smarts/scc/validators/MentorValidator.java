package com.ag04smarts.scc.validators;

import com.ag04smarts.scc.dto.MentorDto;
import com.ag04smarts.scc.services.MentorService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MentorValidator implements Validator {
    private final MentorService mentorService;

    public MentorValidator(MentorService mentorService) {
        this.mentorService = mentorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MentorDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MentorDto mentorDto = (MentorDto) target;

        Long id = mentorDto.getId();
        validateId(id, errors);

        String firstName = mentorDto.getFirstName();
        validateFirstName(firstName, errors);

        String lastName = mentorDto.getLastName();
        validateLastName(lastName, errors);

        Integer age = mentorDto.getAge();
        validateAge(age, errors);
    }

    private void validateId(Long id, Errors errors) {
        if (id != null && !mentorService.existsById(id)) {
            errors.rejectValue("id", ErrorCodes.ERROR_NOT_FOUND, "Mentor doesn't exist");
        }
    }

    private void validateFirstName(String firstName, Errors errors) {
        if (firstName == null || firstName.length() == 0) {
            errors.rejectValue("firstName", ErrorCodes.ERROR_FIRST_NAME_NULL, "First name must be entered");
            return;
        }

        if (firstName.length() < 3) {
            errors.rejectValue("firstName", ErrorCodes.ERROR_FIRST_NAME_SHORT, "First name cannot be shorter than 3 characters");
        } else if (firstName.length() > 20) {
            errors.rejectValue("firstName", ErrorCodes.ERROR_FIRST_NAME_LONG, "First name cannot be longer than 20 characters");
        }
    }

    private void validateLastName(String lastName, Errors errors) {
        if (lastName == null || lastName.length() == 0) {
            errors.rejectValue("lastName", ErrorCodes.ERROR_LAST_NAME_NULL, "Last name must be entered");
            return;
        }

        if (lastName.length() < 3) {
            errors.rejectValue("lastName", ErrorCodes.ERROR_LAST_NAME_SHORT, "Last name cannot be shorter than 3 characters");
        } else if (lastName.length() > 25) {
            errors.rejectValue("lastName", ErrorCodes.ERROR_LAST_NAME_LONG, "Last name cannot be longer than 25 characters");
        }
    }

    private void validateAge(Integer age, Errors errors) {
        //TO DO LATER MAYBE....
    }
}
