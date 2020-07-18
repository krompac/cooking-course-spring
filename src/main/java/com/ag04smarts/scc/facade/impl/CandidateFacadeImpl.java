package com.ag04smarts.scc.facade.impl;

import com.ag04smarts.scc.converters.CandidateDtoToCandidate;
import com.ag04smarts.scc.converters.CandidateToCandidateDto;
import com.ag04smarts.scc.converters.RegistrationDtoToNewRegistration;
import com.ag04smarts.scc.dto.CandidateDto;
import com.ag04smarts.scc.exceptions.NotValidException;
import com.ag04smarts.scc.facade.CandidateFacade;
import com.ag04smarts.scc.model.Candidate;
import com.ag04smarts.scc.model.Registration;
import com.ag04smarts.scc.services.CandidateService;
import com.ag04smarts.scc.services.RegistrationService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CandidateFacadeImpl implements CandidateFacade {
    private final CandidateService candidateService;
    private final RegistrationService registrationService;
    private final CandidateToCandidateDto candidateToCandidateDto;
    private final CandidateDtoToCandidate candidateDtoToCandidate;
    private final RegistrationDtoToNewRegistration registrationDtoToNewRegistration;

    public CandidateFacadeImpl(CandidateDtoToCandidate candidateDtoToCandidate,
                               CandidateToCandidateDto candidateToCandidateDto,
                               RegistrationDtoToNewRegistration registrationDtoToNewRegistration,
                               CandidateService candidateService, RegistrationService registrationService) {
        this.candidateService = candidateService;
        this.registrationService = registrationService;
        this.candidateToCandidateDto = candidateToCandidateDto;
        this.candidateDtoToCandidate = candidateDtoToCandidate;
        this.registrationDtoToNewRegistration = registrationDtoToNewRegistration;
    }

    @Override
    public List<CandidateDto> findAll() {
        List<CandidateDto> candidateDtos = new ArrayList<>();
        candidateService.findAll().stream().forEach(candidate -> candidateDtos.add(candidateToCandidateDto.convert(candidate)));

        return candidateDtos;
    }

    @Override
    public CandidateDto save(CandidateDto candidateDto) {
        final Candidate candidate = candidateDtoToCandidate.convert(candidateDto);
        if (candidate == null) {
            throw new NotValidException("Invalid candidate data");
        }

        Registration registration = registrationDtoToNewRegistration.convert(candidateDto.getRegistration());

        if (registration != null) {
            Registration savedRegistration = registrationService.save(registration);

            candidate.addRegistration(savedRegistration);
        }

        Candidate savedCandidate = candidateService.save(candidate);
        candidateDto.setId(savedCandidate.getId());

        return candidateDto;
    }

    @Override
    public CandidateDto update(CandidateDto candidateDto) {
        return candidateToCandidateDto.convert(candidateService.update(candidateDto));
    }

    @Override
    public List<CandidateDto> updateAll(List<CandidateDto> candidateDtos) {
        return candidateDtos.stream().map(this::update).collect(Collectors.toList());
    }

    @Override
    public boolean delete(Long id) {
        Candidate candidate = candidateService.findById(id);
        if (candidate == null) {
            return false;
        }

        candidateService.delete(candidate);
        return true;
    }
}
