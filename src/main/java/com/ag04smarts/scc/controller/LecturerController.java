package com.ag04smarts.scc.controller;

import com.ag04smarts.scc.dto.LecturerDto;
import com.ag04smarts.scc.exceptions.NotFoundException;
import com.ag04smarts.scc.exceptions.NotValidException;
import com.ag04smarts.scc.facade.LecturerFacade;
import com.ag04smarts.scc.model.Lecturer;
import com.ag04smarts.scc.validators.LecturerValidator;
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

@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class LecturerController {
    private final LecturerFacade lecturerFacade;
    private final LecturerValidator lecturerValidator;

    public LecturerController(LecturerFacade lecturerFacade, LecturerValidator lecturerValidator) {
        this.lecturerFacade = lecturerFacade;
        this.lecturerValidator = lecturerValidator;
    }

    @GetMapping("/lecturers")
    public ResponseEntity<Iterable<LecturerDto>> listAllLecturers() {
        HashSet<LecturerDto> lecturerDtos = lecturerFacade.findAll();

        return new ResponseEntity<>(lecturerDtos, HttpStatus.OK);
    }

    @PostMapping("/lecturer/add")
    public ResponseEntity<LecturerDto> addLecturer(@RequestBody @Valid LecturerDto lecturerDto) {
        LecturerDto savedLecturer = lecturerFacade.save(lecturerDto);

        return new ResponseEntity<>(savedLecturer, HttpStatus.CREATED);
    }

    @PutMapping("/lecturer/update")
    public ResponseEntity<?> updateLecturer(@RequestBody @Valid LecturerDto lecturerDto)
            throws NotValidException {

        LecturerDto updatedLecturer = lecturerFacade.update(lecturerDto);

        return new ResponseEntity<>(updatedLecturer, HttpStatus.OK);
    }

    @DeleteMapping("/lecturer/{id}/delete")
    public ResponseEntity<?> deleteLecturer(@PathVariable Long id) throws NotFoundException {
        Lecturer lecturer = lecturerFacade.findById(id)
                .orElseThrow(() -> new NotFoundException("Lecturer with id: " + id + " doesn't exist"));

        if (lecturerFacade.delete(lecturer)) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot delete lecturer who is the only lecturer on course.", HttpStatus.BAD_REQUEST);
        }
    }

    @InitBinder("lecturerDto")
    public void initLecturerValidator(WebDataBinder dataBinder) {
        dataBinder.setValidator(lecturerValidator);
    }
}
