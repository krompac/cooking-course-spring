package com.ag04smarts.scc.validators;

import com.ag04smarts.scc.dto.CandidateDto;
import com.ag04smarts.scc.dto.RegistrationDto;
import com.ag04smarts.scc.model.enums.Gender;
import com.ag04smarts.scc.services.CandidateService;
import com.ag04smarts.scc.services.MentorService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;

@Component
public class CandidateValidator implements Validator {
    private final MentorService mentorService;
    private final CandidateService candidateService;
    private final RegistrationValidator registrationValidator;

    public CandidateValidator(MentorService mentorService, CandidateService candidateService, RegistrationValidator registrationValidator) {
        this.mentorService = mentorService;
        this.candidateService = candidateService;
        this.registrationValidator = registrationValidator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CandidateDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CandidateDto candidateDto = (CandidateDto) target;

        Long candidateId = candidateDto.getId();
        validateId(candidateId, errors);

        String firstName = candidateDto.getFirstName();
        validateFirstName(firstName, errors);

        String lastName = candidateDto.getLastName();
        validateLastName(lastName, errors);

        String email = candidateDto.getEmail();
        validateEmail(email, errors);

        String phoneNumber = candidateDto.getPhoneNumber();
        validatePhoneNumber(phoneNumber, errors);

        Integer age = candidateDto.getAge();
        validateAge(age, errors);

        String gender = candidateDto.getGender();
        validateGender(gender, errors);

        Long mentorId = candidateDto.getMentorId();
        validateMentor(mentorId, errors);

        RegistrationDto registrationDto = candidateDto.getRegistration();
        validateRegistration(registrationDto, candidateId, errors);
    }

    private void validateId(Long candidateId, Errors errors) {
        if (candidateId != null && !candidateService.existsById(candidateId)) {
            errors.rejectValue("id", ErrorCodes.ERROR_NOT_FOUND, "Candidate doesn't exist");
        }
    }

    private void validateFirstName(String firstName, Errors errors) {
        if (firstName == null || firstName.length() == 0) {
            errors.rejectValue("firstName", ErrorCodes.ERROR_FIRST_NAME_NULL,"First name must be entered");
            return;
        }

        if (firstName.length() < 3) {
            errors.rejectValue("firstName", ErrorCodes.ERROR_FIRST_NAME_SHORT,"First name cannot be shorter than 3 characters");
        }

        if (firstName.length() > 20) {
            errors.rejectValue("firstName", ErrorCodes.ERROR_FIRST_NAME_LONG, "First name cannot be longer than 20 characters");
        }
    }

    private void validateLastName(String lastName, Errors errors) {
        if (lastName == null) {
            errors.rejectValue("lastName", ErrorCodes.ERROR_LAST_NAME_NULL,"Last name must be entered");
            return;
        }

        if (lastName.length() < 3) {
            errors.rejectValue("lastName", ErrorCodes.ERROR_LAST_NAME_SHORT ,"Last name cannot be shorter than 3 characters");
        }

        if (lastName.length() > 20) {
            errors.rejectValue("lastName", ErrorCodes.ERROR_LAST_NAME_LONG, "Last name cannot be longer than 20 characters");
        }
    }

    private void validateEmail(String email, Errors errors) {
        if (email == null) {
            errors.rejectValue("email", ErrorCodes.ERROR_EMAIL_NULL, "Email must be entered");
            return;
        }

        if (!email.matches(Regex.EMAIL_REGEX)) {
            errors.rejectValue("email", ErrorCodes.ERROR_EMAIL_INVALID, "Email is invalid");
        }
    }

    private void validatePhoneNumber(String phoneNumber, Errors errors) {
        if (phoneNumber == null) {
            errors.rejectValue("phoneNumber", ErrorCodes.ERROR_PHONE_NUMBER_NULL, "Phone number must be entered");
            return;
        }

        if (!phoneNumber.matches(Regex.PHONE_REGEX)) {
            errors.rejectValue("phoneNumber", ErrorCodes.ERROR_PHONE_NUMBER_INVALID, "Phone number is invalid");
        }
    }

    private void validateAge(Integer age, Errors errors) {
        if (age == null) {
            errors.rejectValue("age", ErrorCodes.ERROR_AGE_NULL, "Age must be entered");
            return;
        }

        if (age < 18) {
            errors.rejectValue("age", ErrorCodes.ERROR_AGE_YOUNG, "Age must be older than 17");
        } else if (age > 75) {
            errors.rejectValue("age", ErrorCodes.ERROR_AGE_OLD, "Age cannot be older than 75, sorry");
        }
    }

    private void validateGender(String gender, Errors errors) {
        if (gender == null) {
            errors.rejectValue("gender", ErrorCodes.ERROR_GENDER_NULL, "Gender must be entered");
            return;
        }

        if (Arrays.stream(Gender.values()).noneMatch(value -> value.name().equals(gender.toUpperCase()))) {
            errors.rejectValue("gender", ErrorCodes.ERROR_GENDER_INVALID, "Gender is invalid");
        }
    }

    private void validateMentor(Long mentorId, Errors errors) {
        if (mentorId == null) {
            errors.rejectValue("mentorId", ErrorCodes.ERROR_MENTOR_ID_NULL, "Mentor must be entered");
            return;
        }

        if (mentorService.findById(mentorId).isEmpty()) {
            errors.rejectValue("mentorId", ErrorCodes.ERROR_MENTOR_NULL, "Mentor not found");
        }
    }

    private void validateRegistration(RegistrationDto registrationDto, Long candidateId, Errors errors) {
        if (registrationDto != null) {
            Long registrationId = registrationDto.getId();
            if (registrationId != null && !candidateService.findById(candidateId).getRegistration().getId().equals(registrationId)) {
                errors.rejectValue("registration", ErrorCodes.ERROR_NOT_FOUND, "Registration not found");
            }

            registrationValidator.validate(registrationDto, errors);
        }
    }
}
