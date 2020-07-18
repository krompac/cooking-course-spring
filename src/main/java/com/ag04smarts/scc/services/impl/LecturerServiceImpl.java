package com.ag04smarts.scc.services.impl;

import com.ag04smarts.scc.dto.LecturerDto;
import com.ag04smarts.scc.model.Lecturer;
import com.ag04smarts.scc.repository.LecturerRepository;
import com.ag04smarts.scc.services.LecturerService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LecturerServiceImpl implements LecturerService {
    private final LecturerRepository lecturerRepository;

    public LecturerServiceImpl(LecturerRepository lecturerRepository) {
        this.lecturerRepository = lecturerRepository;
    }

    @Override
    public Optional<Lecturer> findById(Long id) {
        return lecturerRepository.findById(id);
    }

    @Override
    public List<Lecturer> findAll() {
        return lecturerRepository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return lecturerRepository.existsById(id);
    }

    @Override
    public Lecturer save(Lecturer lecturer) {
        return lecturerRepository.save(lecturer);
    }

    @Override
    public Lecturer update(LecturerDto lecturerDto) {
        Lecturer currentLecturer = lecturerRepository.getOne(lecturerDto.getId());
        currentLecturer.setFirstName(lecturerDto.getFirstName());
        currentLecturer.setLastName(lecturerDto.getLastName());

        return lecturerRepository.save(currentLecturer);
    }

    @Override
    public void delete(Lecturer lecturer) {
        lecturerRepository.delete(lecturer);
    }
}
