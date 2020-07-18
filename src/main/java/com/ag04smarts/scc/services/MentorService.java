package com.ag04smarts.scc.services;

import com.ag04smarts.scc.model.Mentor;

import java.util.List;
import java.util.Optional;

public interface MentorService {

    Optional<Mentor> findById(Long id);

    boolean existsById(Long id);

    List<Mentor> findAll();

    Mentor save(Mentor mentor);

    void deleteById(Long id);
}
