package com.ag04smarts.scc.services;

import com.ag04smarts.scc.dto.LecturerDto;
import com.ag04smarts.scc.model.Lecturer;

import java.util.List;
import java.util.Optional;

public interface LecturerService {

    Optional<Lecturer> findById(Long id);

    List<Lecturer> findAll();

    Lecturer save(Lecturer lecturer);

    Lecturer update(LecturerDto lecturerDto);

    void delete(Lecturer lecturer);

    boolean existsById(Long id);
}
