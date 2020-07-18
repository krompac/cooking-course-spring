package com.ag04smarts.scc.converters;

import com.ag04smarts.scc.dto.CandidateDto;
import com.ag04smarts.scc.model.Candidate;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Base64;

@Component
public class CandidateToCandidateDto implements Converter<Candidate, CandidateDto> {
    private RegistrationToRegistrationDto registrationToRegistrationDto;

    public CandidateToCandidateDto(RegistrationToRegistrationDto registrationToRegistrationDto) {
        this.registrationToRegistrationDto = registrationToRegistrationDto;
    }

    @Transactional
    @Synchronized
    @Nullable
    @Override
    public CandidateDto convert(Candidate candidate) {
        if (candidate == null) {
            return null;
        }

        final CandidateDto candidateDto = new CandidateDto();
        candidateDto.setId(candidate.getId());
        candidateDto.setFirstName(candidate.getFirstName());
        candidateDto.setLastName(candidate.getLastName());
        candidateDto.setGender(candidate.getGender().name());
        candidateDto.setAge(candidate.getAge());
        candidateDto.setEmail(candidate.getEmail());
        candidateDto.setPhoneNumber(candidate.getPhoneNumber());

        if (candidate.getPicture() != null) {
            Byte[] pictureData = candidate.getPicture();
            byte[] pictureDataConverted = new byte[pictureData.length];

            for (int i = 0; i < pictureData.length; i++) {
                pictureDataConverted[i] = pictureData[i];
            }

            String base64picture = Base64.getEncoder().encodeToString(pictureDataConverted);

            candidateDto.setPicture(base64picture);
        }

        if (candidate.getRegistration() != null) {
            candidateDto.setRegistration(registrationToRegistrationDto.convert(candidate.getRegistration()));
        }

        if (candidate.getMentor() != null) {
            candidateDto.setMentorId(candidate.getMentor().getId());
        }

        return candidateDto;
    }
}
