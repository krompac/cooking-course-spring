package com.ag04smarts.scc.repository;

import com.ag04smarts.scc.model.Candidate;
import com.ag04smarts.scc.model.Course;
import com.ag04smarts.scc.model.Registration;
import com.ag04smarts.scc.model.enums.CourseType;
import com.ag04smarts.scc.model.enums.Gender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CandidateRepositoryTest {

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    RegistrationRepository registrationRepository;

    @Autowired
    CourseRepository courseRepository;

    private void initCourses(){
        Course firstCourse = new Course();
        firstCourse.setType(CourseType.BASIC);

        Course secondCourse = new Course();
        secondCourse.setType(CourseType.INTERMEDIATE);

        courseRepository.save(firstCourse);
        courseRepository.save(secondCourse);
    }

    private Candidate getCandidate(String firstName, String lastName){
        Candidate candidate = new Candidate();
        candidate.setFirstName(firstName);
        candidate.setLastName(lastName);

        return  candidate;
    }

    private void addRegistration(int year, int month, int day, Candidate candidate){
        Registration registration = new Registration();
        LocalDate date = LocalDate.of(year, month, day);

        registration.addCandidate(candidate);
        registration.setRegistrationDate(date);
        registrationRepository.save(registration);
    }

    @Before
    public void setUp() throws Exception {
        initCourses();
    }

    @Test
    public void findCandidateByAgeGreaterThanAndRegistrationRegistrationDateGreaterThan() {
        Candidate firstCandidate = getCandidate("Pero", "Peric");
        firstCandidate.setAge(18);
        addRegistration(2019, 7, 21, firstCandidate);
        candidateRepository.save(firstCandidate);

        Candidate secondCandidate = getCandidate("Ivo", "Ivic");
        secondCandidate.setAge(23);
        addRegistration(2019, 7, 24, secondCandidate);
        candidateRepository.save(secondCandidate);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, 7, 21);

        List<Candidate> candidates;
        candidates = candidateRepository
                .findAllByAgeGreaterThanAndRegistrationRegistrationDateGreaterThan(17, calendar.getTime());

        assertEquals(candidates.size(), 1);

        calendar.set(2020, 1, 1);

        candidates = candidateRepository
                .findAllByAgeGreaterThanAndRegistrationRegistrationDateGreaterThan(22, calendar.getTime());

        assertEquals(candidates.size(), 0);

        calendar.set(2019, 7, 20);

        candidates = candidateRepository
                .findAllByAgeGreaterThanAndRegistrationRegistrationDateGreaterThan(16, calendar.getTime());

        assertEquals(candidates.size(), 2);
    }

    @Test
    public void findCandidateByGenderAndRegistrationCoursesType() {
        Candidate firstCandidate = new Candidate();
        firstCandidate.setGender(Gender.FEMALE);
        addRegistration(1, 1,1, firstCandidate);

        Course basicCourse = courseRepository.findCourseByType(CourseType.BASIC).get(0);

        firstCandidate.getRegistration().addCourse(basicCourse);
        candidateRepository.save(firstCandidate);

        Candidate secondCandidate = new Candidate();
        secondCandidate.setGender(Gender.FEMALE);
        addRegistration(1, 1,1, secondCandidate);

        Course intermediateCourse = courseRepository.findCourseByType(CourseType.INTERMEDIATE).get(0);

        secondCandidate.getRegistration().addCourse(intermediateCourse);
        candidateRepository.save(firstCandidate);

        List<Candidate> candidates;
        candidates = candidateRepository.findAllByGenderAndRegistrationCoursesType(Gender.FEMALE, CourseType.BASIC);

        assertEquals(candidates.size(), 1);

        candidates = candidateRepository.findAllByGenderAndRegistrationCoursesType(Gender.MALE, CourseType.BASIC);

        assertEquals(candidates.size(), 0);

        candidates = candidateRepository.findAllByGenderAndRegistrationCoursesType(Gender.FEMALE, CourseType.INTERMEDIATE);

        assertEquals(candidates.size(), 1);
    }
}
