package com.ag04smarts.scc.services;

import com.ag04smarts.scc.dto.RegistrationDto;
import com.ag04smarts.scc.model.Candidate;
import com.ag04smarts.scc.model.Registration;

import java.util.List;

public interface RegistrationService {
    Registration save(Registration registration);

    void deleteRegistration(Registration registration);

    List<Registration> findAllRegistrations();

    List<Registration> findAllRegistrationsByCourseId(Long id);

    Registration findRegistrationById(Long id);

    boolean existsById(Long id);

    Candidate saveRegistrationCommand(RegistrationDto command);

    List<Registration> findAllWithId(List<Long> ids);
}
