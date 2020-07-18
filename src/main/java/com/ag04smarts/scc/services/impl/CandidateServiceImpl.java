package com.ag04smarts.scc.services.impl;

import com.ag04smarts.scc.converters.CandidateToCandidateDto;
import com.ag04smarts.scc.dto.CandidateDto;
import com.ag04smarts.scc.converters.CandidateDtoToCandidate;
import com.ag04smarts.scc.converters.RegistrationDtoToExistingRegistration;
import com.ag04smarts.scc.converters.RegistrationDtoToNewRegistration;
import com.ag04smarts.scc.model.Candidate;
import com.ag04smarts.scc.model.Course;
import com.ag04smarts.scc.model.Registration;
import com.ag04smarts.scc.model.enums.CourseType;
import com.ag04smarts.scc.model.enums.Gender;
import com.ag04smarts.scc.repository.CandidateRepository;
import com.ag04smarts.scc.services.CandidateService;
import com.ag04smarts.scc.services.CourseService;
import com.ag04smarts.scc.services.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;
    private final CourseService courseService;
    private final CandidateToCandidateDto candidateToCandidateDto;
    private final CandidateDtoToCandidate candidateDtoToCandidate;
    private final RegistrationDtoToNewRegistration registrationDtoToNewRegistration;
    private final RegistrationDtoToExistingRegistration registrationDtoToExistingRegistration;

    public CandidateServiceImpl(CandidateRepository candidateRepository, CourseService courseService,
                                CandidateToCandidateDto candidateToCandidateDto, CandidateDtoToCandidate candidateDtoToCandidate,
                                RegistrationDtoToNewRegistration registrationDtoToNewRegistration,
                                RegistrationDtoToExistingRegistration registrationDtoToExistingRegistration) {
        this.candidateRepository = candidateRepository;
        this.courseService = courseService;
        this.candidateToCandidateDto = candidateToCandidateDto;
        this.candidateDtoToCandidate = candidateDtoToCandidate;
        this.registrationDtoToNewRegistration = registrationDtoToNewRegistration;
        this.registrationDtoToExistingRegistration = registrationDtoToExistingRegistration;
    }

    @Override
    public List<Candidate> findAll() {
        return candidateRepository.findAll();
    }

    @Override
    @Nullable
    public Candidate findById(Long id) {
        return candidateRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return candidateRepository.existsById(id);
    }

    @Override
    public Candidate save(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @Override
    public void delete(Candidate candidate) {
        Registration candidateRegistration = candidate.getRegistration();

        if (candidateRegistration != null && candidateRegistration.getCourses() != null
                && candidateRegistration.getCourses().size() > 0){
            candidateRegistration.getCourses().stream().forEach(course -> {
                int studentNumber = course.getNumberOfStudents();
                course.setNumberOfStudents(studentNumber + 1);
            });
        }

        candidateRepository.delete(candidate);
    }

    @Override
    public List<Candidate> findWithAge21AndRegDate() {
        Integer age = 21;
        Calendar date = Calendar.getInstance();
        date.set(2019, Calendar.JULY, 20);

        return candidateRepository.findAllByAgeGreaterThanAndRegistrationRegistrationDateGreaterThan(age, date.getTime());
    }

    @Override
    public List<Candidate> findFemaleWithBasicCourses() {
        return candidateRepository.findAllByGenderAndRegistrationCoursesType(Gender.FEMALE, CourseType.BASIC);
    }

    @Override
    public Candidate update(CandidateDto candidateDto) {
        Candidate currentCandidate = candidateRepository.findById(candidateDto.getId()).orElse(null);
        Candidate updatedCandidate = candidateDtoToCandidate.convert(candidateDto);

        if (updatedCandidate != null && currentCandidate != null) {
            Registration currentRegistration = currentCandidate.getRegistration();

            Registration updatedRegistration;
            boolean registrationExists = currentRegistration != null;

            if (registrationExists) {
                updatedRegistration = registrationDtoToExistingRegistration.convert(candidateDto.getRegistration());
            } else {
                updatedRegistration = registrationDtoToNewRegistration.convert(candidateDto.getRegistration());
            }

            if (updatedRegistration != null) {
                if (!registrationExists) {
                    updatedCandidate.addRegistration(updatedRegistration);
                } else {
                    Set<Course> currentCourses = currentRegistration.getCourses();
                    Set<Course> updatedCourses = updatedRegistration.getCourses();

                    updatedCourses.forEach(course -> {
                        if (!currentCourses.contains(course)) {
                            int updatedNumberOfStudents = course.getNumberOfStudents();
                            course.setNumberOfStudents(updatedNumberOfStudents - 1);
                        }
                    });

                    if (currentCourses.size() > 0) {
                        if (updatedCourses.size() == 0) {
                            updateDroppedCourses(currentCourses);
                        } else {
                            Set<Course> leftOverCourses = new HashSet<>(currentCourses);
                            leftOverCourses.removeAll(updatedCourses);

                            updateDroppedCourses(leftOverCourses);
                        }
                    }

                    updatedCandidate.addRegistration(updatedRegistration);
                }
            }

            return candidateRepository.save(updatedCandidate);
        }

        return currentCandidate;
    }

    private void updateDroppedCourses(Set<Course> courses) {
        courses.forEach(course -> {
            int updatedNumberOfStudents = course.getNumberOfStudents();
            course.setNumberOfStudents(updatedNumberOfStudents + 1);
        });

        courseService.saveAll(courses);
    }
}

