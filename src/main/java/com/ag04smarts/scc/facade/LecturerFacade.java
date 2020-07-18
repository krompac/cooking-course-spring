package com.ag04smarts.scc.facade;

import com.ag04smarts.scc.dto.LecturerDto;
import com.ag04smarts.scc.model.Lecturer;

import java.util.HashSet;
import java.util.Optional;

public interface LecturerFacade {

    Optional<Lecturer> findById(Long id);

    HashSet<LecturerDto> findAll();

    LecturerDto update(LecturerDto lecturerDto);

    boolean delete(Lecturer lecturer);

    LecturerDto save(LecturerDto lecturerDto);
}
