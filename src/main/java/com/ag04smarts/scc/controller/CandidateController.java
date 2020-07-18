package com.ag04smarts.scc.controller;

import com.ag04smarts.scc.dto.CandidateDto;
import com.ag04smarts.scc.exceptions.NotFoundException;
import com.ag04smarts.scc.exceptions.NotValidException;
import com.ag04smarts.scc.facade.CandidateFacade;
import com.ag04smarts.scc.validators.CandidateValidator;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@Controller
public class CandidateController {
    private final CandidateFacade candidateFacade;
    private final CandidateValidator candidateValidator;

    public CandidateController(CandidateFacade candidateFacade, CandidateValidator candidateValidator) {
        this.candidateFacade = candidateFacade;
        this.candidateValidator = candidateValidator;
    }

    @GetMapping("/candidates")
    public ResponseEntity<List<CandidateDto>> listAllCandidates() {
        List<CandidateDto> candidateDtos = candidateFacade.findAll();

        return new ResponseEntity<>(candidateDtos, HttpStatus.OK);
    }

    @PutMapping("/candidate/update")
    public ResponseEntity<?> updateCandidate(@RequestBody @Valid CandidateDto existingCandidate) throws NotValidException {
        CandidateDto updatedCandidate = candidateFacade.update(existingCandidate);

        return new ResponseEntity<>(updatedCandidate, HttpStatus.OK);
    }

    @PutMapping("/candidates/update")
    public ResponseEntity<?> updateCandidates(@RequestBody @Valid List<CandidateDto> existingCandidates) throws NotValidException {
        List<CandidateDto> updatedCandidates = candidateFacade.updateAll(existingCandidates);

        return new ResponseEntity<>(updatedCandidates, HttpStatus.OK);
    }

    @DeleteMapping("/candidate/{id}/delete")
    public ResponseEntity<?> deleteCandidate(@PathVariable Long id) throws NotFoundException {
        if (candidateFacade.delete(id)) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/candidate/add")
    public ResponseEntity<?> saveChanges(@Valid @RequestBody CandidateDto newCandidate) throws NotValidException {
        CandidateDto savedCandidate = candidateFacade.save(newCandidate);

        return new ResponseEntity<>(savedCandidate, HttpStatus.CREATED);
    }

    @InitBinder("candidateDto")
    public void initCandidateDtoValidator(WebDataBinder dataBinder) {
        dataBinder.setValidator(candidateValidator);
    }
}
