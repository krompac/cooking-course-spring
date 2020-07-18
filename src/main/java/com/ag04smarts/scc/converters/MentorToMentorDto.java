package com.ag04smarts.scc.converters;

import com.ag04smarts.scc.dto.MentorDto;
import com.ag04smarts.scc.model.Mentor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class MentorToMentorDto implements Converter<Mentor, MentorDto> {

    @Transactional
    @Override
    public MentorDto convert(Mentor source) {
        if (source == null) {
            return null;
        }

        MentorDto mentorDto = new MentorDto();
        mentorDto.setId(source.getId());
        mentorDto.setFirstName(source.getFirstName());
        mentorDto.setLastName(source.getLastName());
        mentorDto.setAge(source.getAge());

        if (source.getCandidates().size() > 0) {
            source.getCandidates().forEach(candidate -> mentorDto.getCandidateIds().add(candidate.getId()));
        }

        return mentorDto;
    }
}
