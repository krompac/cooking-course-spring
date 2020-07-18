package com.ag04smarts.scc.services.impl;

import com.ag04smarts.scc.model.Mentor;
import com.ag04smarts.scc.repository.MentorRepository;
import com.ag04smarts.scc.services.MentorService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MentorServiceImpl implements MentorService {
    private final MentorRepository mentorRepository;

    public MentorServiceImpl(MentorRepository mentorRepository) {
        this.mentorRepository = mentorRepository;
    }

    @Override
    public Optional<Mentor> findById(Long id) {
        return mentorRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return mentorRepository.existsById(id);
    }

    @Override
    public List<Mentor> findAll() {
        return mentorRepository.findAll();
    }

    @Override
    public Mentor save(Mentor mentor) {
        return mentorRepository.save(mentor);
    }

    @Override
    public void deleteById(Long id) {
        mentorRepository.deleteById(id);
    }
}
