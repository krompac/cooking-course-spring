package com.ag04smarts.scc.facade;

import com.ag04smarts.scc.dto.CandidateDto;

import java.util.List;

public interface CandidateFacade {

    List<CandidateDto> findAll();

    CandidateDto update(CandidateDto candidateDto);

    List<CandidateDto> updateAll(List<CandidateDto> candidateDtos);

    boolean delete(Long id);

    CandidateDto save(CandidateDto candidateDto);
}
