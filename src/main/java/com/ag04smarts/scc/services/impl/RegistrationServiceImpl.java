package com.ag04smarts.scc.services.impl;

import com.ag04smarts.scc.dto.RegistrationDto;
import com.ag04smarts.scc.converters.RegistrationDtoToNewRegistration;
import com.ag04smarts.scc.exceptions.NotValidException;
import com.ag04smarts.scc.model.Candidate;
import com.ag04smarts.scc.model.Registration;
import com.ag04smarts.scc.repository.CandidateRepository;
import com.ag04smarts.scc.repository.RegistrationRepository;
import com.ag04smarts.scc.services.RegistrationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final CandidateRepository candidateRepository;
    private final RegistrationRepository registrationRepository;
    private final RegistrationDtoToNewRegistration commandToRegistration;

    public RegistrationServiceImpl(RegistrationRepository registrationRepository, CandidateRepository candidateRepository,
                                   RegistrationDtoToNewRegistration commandToRegistration) {
        this.registrationRepository = registrationRepository;
        this.candidateRepository = candidateRepository;
        this.commandToRegistration = commandToRegistration;
    }

    @Override
    public Registration save(Registration registration) {
        return registrationRepository.save(registration);
    }

    @Override
    public void deleteRegistration(Registration registration) {
        registrationRepository.delete(registration);
    }

    @Override
    public List<Registration> findAllRegistrations() {
        return registrationRepository.findAll();
    }

    @Override
    public Registration findRegistrationById(Long id) {
        if (id == null) {
            return null;
        }

        return registrationRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return registrationRepository.existsById(id);
    }

    @Override
    @Transactional
    public Candidate saveRegistrationCommand(RegistrationDto command) {
        Registration registration = commandToRegistration.convert(command);

        if (registration != null) {
            Candidate candidate = registration.getCandidate();

            if (candidate.getFirstName().strip().equals("") || candidate.getLastName().strip().equals("")){
                throw new NotValidException();
            } else {
                candidateRepository.save(candidate);
            }

            registrationRepository.save(registration);

            return candidate;
        }

        return null;
    }

    @Override
    public List<Registration> findAllRegistrationsByCourseId(Long id) {
        return registrationRepository.findAllByCoursesId(id);
    }

    @Override
    public List<Registration> findAllWithId(List<Long> ids) {
        return registrationRepository.findAllByIdIn(ids);
    }
}
