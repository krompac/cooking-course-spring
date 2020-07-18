package com.ag04smarts.scc.converters;

import com.ag04smarts.scc.dto.CandidateDto;
import com.ag04smarts.scc.model.Candidate;
import com.ag04smarts.scc.model.enums.Gender;
import com.ag04smarts.scc.repository.MentorRepository;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Base64;

@Component
public class CandidateDtoToCandidate implements Converter<CandidateDto, Candidate> {
    private final MentorRepository mentorRepository;

    public CandidateDtoToCandidate(MentorRepository mentorRepository) {
        this.mentorRepository = mentorRepository;
    }

    @Transactional
    @Synchronized
    @Nullable
    @Override
    public Candidate convert(CandidateDto source) {
        if (source == null){
            return null;
        }

        final Candidate candidate = new Candidate();
        candidate.setId(source.getId());
        candidate.setFirstName(source.getFirstName());
        candidate.setLastName(source.getLastName());
        candidate.setAge(source.getAge());

        if (source.getGender() == null) {
            candidate.setGender(null);
        } else {
            candidate.setGender(Gender.valueOf(source.getGender()));
        }

        candidate.setPhoneNumber(source.getPhoneNumber());
        candidate.setEmail(source.getEmail());

        if (source.getPicture() != null) {
            byte[] pictureData = Base64.getDecoder().decode(source.getPicture());
            Byte[] pictureDataConverted = new Byte[pictureData.length];

            for (int i = 0; i < pictureData.length; i++) {
                pictureDataConverted[i] = pictureData[i];
            }

            candidate.setPicture(pictureDataConverted);
        }

        Long mentorId = source.getMentorId();

        if (mentorId != null){
            mentorRepository.findById(mentorId).ifPresent(candidate::addMentor);
        }

        return candidate;
    }
}
