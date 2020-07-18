package com.ag04smarts.scc.services;

import com.ag04smarts.scc.model.Candidate;
import com.ag04smarts.scc.model.Course;
import com.ag04smarts.scc.model.Registration;
import com.ag04smarts.scc.model.enums.CourseType;
import com.ag04smarts.scc.model.enums.Gender;
import com.ag04smarts.scc.repository.CandidateRepository;
import com.ag04smarts.scc.repository.CourseRepository;
import com.ag04smarts.scc.repository.RegistrationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class CandidateServiceImplIT {

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    RegistrationRepository registrationRepository;

    @Autowired
    CourseRepository courseRepository;

    private CandidateService service;

    @BeforeEach
    void setUp() {
        Candidate candidate = new Candidate();
        candidate.setAge(22);

//        Calendar date = Calendar.getInstance();
//        date.set(2020, Calendar.JULY, 21);

        LocalDate date = LocalDate.of(2020, 7, 21);

        Registration registration = new Registration();
        registration.setRegistrationDate(date);
        registration.addCandidate(candidate);
    }

    @Test
    void findAllCandidates() {
        List<Candidate> candidates = service.findAll();

        assertEquals(candidates.size(), 1);
    }

    @Test
    void findCandidateById(){
        Candidate candidate = service.findAll().get(0);
        Long id = candidate.getId();

        Candidate foundCandidate = service.findById(id);

        assertEquals(candidate, foundCandidate);
        assertEquals(id, foundCandidate.getId());
    }

    @Test
    void addCandidate() {
        Candidate candidateToAdd = new Candidate();
        service.save(candidateToAdd);

        assertEquals(service.findAll().size(), 2);
    }

    @Test
    void deleteCandidate() {
        Candidate candidateToDelete = service.findAll().get(0);
        service.delete(candidateToDelete);

        assertEquals(service.findAll().size(), 0);
    }

    @Test
    void findWithAge21AndRegDate() {
        Calendar dateToCheck = Calendar.getInstance();
        dateToCheck.set(2019, Calendar.JULY, 20);

        List<Candidate> candidates = service.findWithAge21AndRegDate();
        assertEquals(candidates.size(), 1);
    }

    @Test
    void findFemaleWithBasicCourses() {
        Candidate candidate = new Candidate();
        candidate.setGender(Gender.FEMALE);

        Registration firstRegistration = new Registration();
        firstRegistration.addCandidate(candidate);

        Course basicCourse = new Course();
        basicCourse.setType(CourseType.BASIC);

        Course advancedCourse = new Course();
        advancedCourse.setType(CourseType.ADVANCED);

        courseRepository.save(basicCourse);
        courseRepository.save(advancedCourse);

        firstRegistration.addCourse(basicCourse);

        Registration secondRegistration = new Registration();

        Candidate secondCandidate = new Candidate();
        secondCandidate.setGender(Gender.FEMALE);
        candidateRepository.save(secondCandidate);

        secondRegistration.addCourse(advancedCourse);
        secondRegistration.addCandidate(secondCandidate);

        Candidate thirdCandidate = new Candidate();
        thirdCandidate.setGender(Gender.MALE);
        candidateRepository.save(thirdCandidate);

        Registration thirdRegistration = new Registration();
        thirdRegistration.addCourse(basicCourse);
        thirdRegistration.addCandidate(thirdCandidate);

        registrationRepository.save(firstRegistration);
        registrationRepository.save(secondRegistration);
        registrationRepository.save(thirdRegistration);

        List<Candidate> candidates = service.findFemaleWithBasicCourses();

        assertEquals(candidates.size(), 1);

        thirdCandidate.setGender(Gender.FEMALE);

        candidates = service.findFemaleWithBasicCourses();

        assertEquals(candidates.size(), 2);
    }
}
