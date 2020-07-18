package com.ag04smarts.scc.repository;

import com.ag04smarts.scc.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findAllByCoursesId(Long courseId);
    List<Registration> findAllByIdIn(List<Long> ids);
}
