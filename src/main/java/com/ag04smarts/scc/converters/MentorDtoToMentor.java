package com.ag04smarts.scc.converters;

import com.ag04smarts.scc.model.Mentor;
import org.springframework.stereotype.Component;
import com.ag04smarts.scc.dto.MentorDto;
import com.ag04smarts.scc.services.CandidateService;
import org.springframework.core.convert.converter.Converter;

@Component
public class MentorDtoToMentor implements Converter<MentorDto, Mentor> {
    private final CandidateService candidateService;

    public MentorDtoToMentor(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @Override
    public Mentor convert(MentorDto source) {
        if (source == null) {
            return null;
        }

        Mentor mentor = new Mentor();

        mentor.setId(source.getId());
        mentor.setFirstName(source.getFirstName());
        mentor.setLastName(source.getLastName());
        mentor.setAge(source.getAge());

        if (source.getCandidateIds().size() > 0) {
            source.getCandidateIds().forEach(candidateId ->
                    mentor.getCandidates().add(candidateService.findById(candidateId)));
        }

        return mentor;
    }
}
