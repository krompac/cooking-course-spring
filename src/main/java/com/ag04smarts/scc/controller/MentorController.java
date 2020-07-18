package com.ag04smarts.scc.controller;


import com.ag04smarts.scc.dto.MentorDto;
import com.ag04smarts.scc.exceptions.NotFoundException;
import com.ag04smarts.scc.exceptions.NotValidException;
import com.ag04smarts.scc.facade.MentorFacade;
import com.ag04smarts.scc.validators.MentorValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
import java.util.Set;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class MentorController {
    private final MentorFacade mentorFacade;
    private final MentorValidator mentorValidator;

    public MentorController(MentorFacade mentorFacade, MentorValidator mentorValidator) {
        this.mentorFacade = mentorFacade;
        this.mentorValidator = mentorValidator;
    }

    @GetMapping("/mentors")
    public ResponseEntity<Set<MentorDto>> getMentors() {
        Set<MentorDto> mentorDtos = mentorFacade.findAll();

        return new ResponseEntity<>(mentorDtos, HttpStatus.OK);
    }

    @PostMapping("/mentor/add")
    public ResponseEntity<MentorDto> addMentor(@RequestBody @Valid MentorDto mentorDto) {
        MentorDto savedMentor = mentorFacade.save(mentorDto);

        return new ResponseEntity<>(savedMentor, HttpStatus.CREATED);
    }

    @PutMapping("mentor/update")
    public ResponseEntity<?> updateMentor(@RequestBody @Valid MentorDto mentorDto) {
        MentorDto updatedMentor = mentorFacade.update(mentorDto);

        return new ResponseEntity<>(updatedMentor, HttpStatus.OK);
    }

    @DeleteMapping("mentor/{id}/delete")
    public ResponseEntity<?> deleteMentor(@PathVariable Long id) throws NotFoundException {
        mentorFacade.findById(id).orElseThrow(() -> new NotFoundException("Mentor with id: " + id + " is not found"));
        mentorFacade.delete(id);

        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @InitBinder("mentorDto")
    public void initMentorValidator(WebDataBinder dataBinder) {
        dataBinder.setValidator(mentorValidator);
    }
}
