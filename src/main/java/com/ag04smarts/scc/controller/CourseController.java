package com.ag04smarts.scc.controller;

import com.ag04smarts.scc.dto.CourseDto;
import com.ag04smarts.scc.exceptions.NotFoundException;
import com.ag04smarts.scc.facade.CourseFacade;
import com.ag04smarts.scc.model.Course;
import com.ag04smarts.scc.validators.CourseValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;

@Controller
@CrossOrigin
public class CourseController {
    private final CourseFacade courseFacade;
    private final CourseValidator courseValidator;

    public CourseController(CourseFacade courseFacade, CourseValidator courseValidator) {
        this.courseFacade = courseFacade;
        this.courseValidator = courseValidator;
    }

    @GetMapping("/courses")
    public ResponseEntity<Iterable<CourseDto>> listAllCourses() {
        HashSet<CourseDto> courseDtos = courseFacade.findAll();

        return new ResponseEntity<>(courseDtos, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/course/add")
    public ResponseEntity<?> addCourse(@RequestBody @Valid CourseDto courseDto) {
        CourseDto savedCourse = courseFacade.save(courseDto);

        return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
    }

    @PutMapping("/course/update")
    public ResponseEntity<?> updateCourse(@RequestBody @Valid CourseDto courseDto) {
        CourseDto updatedCourse = courseFacade.update(courseDto);

        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
    }

    @PutMapping("/course/deleteFromCourse/{course_id}")
    public ResponseEntity<?> removeCourseFromRegistrations(@RequestBody List<Long> registrationIds, @PathVariable Long course_id) {
        Course course = courseFacade.findById(course_id)
                .orElseThrow(() -> new NotFoundException("Course with id " + course_id + " doesn't exist"));

        courseFacade.removeCourseFromRegistrations(course, registrationIds);

        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("course/{course_id}/delete")
    public ResponseEntity<?> deleteCourse(@PathVariable Long course_id) throws NotFoundException {
        Course course = courseFacade.findById(course_id)
                .orElseThrow(() -> new NotFoundException("Course with id " + course_id + " doesn't exist"));
        courseFacade.delete(course);

        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @InitBinder("courseDto")
    public void initCourseValidator(WebDataBinder dataBinder) {
        dataBinder.setValidator(courseValidator);
    }
}
