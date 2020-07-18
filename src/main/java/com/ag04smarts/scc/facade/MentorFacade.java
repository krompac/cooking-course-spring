package com.ag04smarts.scc.facade;

import com.ag04smarts.scc.dto.MentorDto;
import com.ag04smarts.scc.model.Mentor;

import java.util.Optional;
import java.util.Set;

public interface MentorFacade {

    Optional<Mentor> findById(Long id);

    Set<MentorDto> findAll();

    MentorDto save(MentorDto mentor);

    MentorDto update(MentorDto mentorDto);

    boolean delete(Long id);
}
