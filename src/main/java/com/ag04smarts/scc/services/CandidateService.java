package com.ag04smarts.scc.services;

import com.ag04smarts.scc.dto.CandidateDto;
import com.ag04smarts.scc.model.Candidate;

import java.util.List;

public interface CandidateService {
    Candidate save(Candidate candidate);

    Candidate update(CandidateDto candidateDto);

    void delete(Candidate candidate);

    List<Candidate> findAll();

    Candidate findById(Long id);

    boolean existsById(Long id);

    List<Candidate> findWithAge21AndRegDate();

    List<Candidate> findFemaleWithBasicCourses();
}
