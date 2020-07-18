package com.ag04smarts.scc.converters;

import com.ag04smarts.scc.dto.LecturerDto;
import com.ag04smarts.scc.model.Lecturer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LecturerToLecturerDto implements Converter<Lecturer, LecturerDto> {
    @Override
    public LecturerDto convert(Lecturer source) {

        if (source == null) {
            return null;
        }

        final LecturerDto lecturerDto = new LecturerDto();

        lecturerDto.setId(source.getId());
        lecturerDto.setFirstName(source.getFirstName());
        lecturerDto.setLastName(source.getLastName());

        source.getCourses().forEach(course -> {
            lecturerDto.getCourses().add(course.getId());
        });

        return lecturerDto;
    }
}
