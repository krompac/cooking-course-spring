package com.ag04smarts.scc.facade.impl;

import com.ag04smarts.scc.dto.MentorDto;
import com.ag04smarts.scc.converters.MentorDtoToMentor;
import com.ag04smarts.scc.converters.MentorToMentorDto;
import com.ag04smarts.scc.facade.MentorFacade;
import com.ag04smarts.scc.model.Mentor;
import com.ag04smarts.scc.services.MentorService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class MentorFacadeImpl implements MentorFacade {
    private final MentorService mentorService;
    private final MentorToMentorDto mentorToMentorDto;
    private final MentorDtoToMentor mentorDtoToMentor;

    public MentorFacadeImpl(MentorService mentorService, MentorToMentorDto mentorToMentorDto,
                            MentorDtoToMentor mentorDtoToMentor) {
        this.mentorService = mentorService;
        this.mentorToMentorDto = mentorToMentorDto;
        this.mentorDtoToMentor = mentorDtoToMentor;
    }

    @Override
    public Optional<Mentor> findById(Long id) {
        return mentorService.findById(id);
    }

    @Override
    public Set<MentorDto> findAll() {
        Set<MentorDto> mentorDtos = new HashSet<>();
        this.mentorService.findAll().stream().forEach(mentor -> {
            mentorDtos.add(mentorToMentorDto.convert(mentor));
        });

        return mentorDtos;
    }

    @Override
    public MentorDto save(MentorDto mentorDto) {
        Mentor savedMentor = mentorService.save(mentorDtoToMentor.convert(mentorDto));
        mentorDto.setId(savedMentor.getId());

        return mentorDto;
    }

    @Override
    public MentorDto update(MentorDto mentorDto) {
        return mentorToMentorDto.convert(mentorService.save(mentorDtoToMentor.convert(mentorDto)));
    }

    @Override
    public boolean delete(Long id) {
        if (mentorService.existsById(id)) {
            mentorService.deleteById(id);
            return true;
        }

        return false;
    }
}
