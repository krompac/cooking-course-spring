package com.ag04smarts.scc.repository;

import com.ag04smarts.scc.model.Candidate;
import com.ag04smarts.scc.model.enums.CourseType;
import com.ag04smarts.scc.model.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    List<Candidate> findAllByAgeGreaterThanAndRegistrationRegistrationDateGreaterThan(Integer age, Date registrationDate);
    List<Candidate> findAllByGenderAndRegistrationCoursesType(Gender gender, CourseType type);
}
