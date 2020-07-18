package com.ag04smarts.scc.facade.impl;

import com.ag04smarts.scc.dto.LecturerDto;
import com.ag04smarts.scc.converters.LecturerDtoToLecturer;
import com.ag04smarts.scc.converters.LecturerToLecturerDto;
import com.ag04smarts.scc.facade.CourseFacade;
import com.ag04smarts.scc.facade.LecturerFacade;
import com.ag04smarts.scc.model.Course;
import com.ag04smarts.scc.model.Lecturer;
import com.ag04smarts.scc.services.CourseService;
import com.ag04smarts.scc.services.LecturerService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Component
public class LecturerFacadeImpl implements LecturerFacade {
    private final LecturerService lecturerService;
    private final CourseService courseService;
    private final LecturerToLecturerDto lecturerToLecturerDto;
    private final LecturerDtoToLecturer lecturerDtoToLecturer;

    public LecturerFacadeImpl(LecturerService lecturerService, CourseService courseService,
                              LecturerToLecturerDto lecturerToLecturerDto,
                              LecturerDtoToLecturer lecturerDtoToLecturer) {
        this.lecturerService = lecturerService;
        this.courseService = courseService;
        this.lecturerToLecturerDto = lecturerToLecturerDto;
        this.lecturerDtoToLecturer = lecturerDtoToLecturer;
    }

    @Override
    public Optional<Lecturer> findById(Long id) {
        return lecturerService.findById(id);
    }

    @Override
    public HashSet<LecturerDto> findAll() {
        HashSet<LecturerDto> lecturerDtos = new HashSet<>();
        lecturerService.findAll().stream().forEach(lecturer -> {
            lecturerDtos.add(lecturerToLecturerDto.convert(lecturer));
        });

        return lecturerDtos;
    }

    @Override
    public LecturerDto update(LecturerDto lecturerDto) {
        return lecturerToLecturerDto.convert(lecturerService.update(lecturerDto));
    }

    @Override
    public boolean delete(Lecturer lecturer) {
        if (lecturer == null) {
            return false;
        }

        List<Course> lecturerCourses = courseService.findAllByLecturersId(lecturer.getId());

        if (lecturerCourses.stream().anyMatch(course -> course.getLecturers().size() == 1)) {
            return false;
        }

        lecturerCourses.forEach(course -> course.getLecturers().remove(lecturer));
        lecturerService.delete(lecturer);

        return true;
    }

    @Override
    public LecturerDto save(LecturerDto lecturerDto) {
        Lecturer savedLecturer = this.lecturerService.save(lecturerDtoToLecturer.convert(lecturerDto));
        lecturerDto.setId(savedLecturer.getId());

        return lecturerDto;
    }
}
