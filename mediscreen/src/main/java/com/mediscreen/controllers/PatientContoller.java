package com.mediscreen.controllers;


import com.mediscreen.dto.PatientDto;
import com.mediscreen.services.IPatientService;
import com.mediscreen.services.exceptions.PatientDtoAlreadyExistException;
import com.mediscreen.services.exceptions.PatientDtoNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Log4j2
@RestController
@RequestMapping(value = "patient")
@CrossOrigin(origins = "http://localhost:4200")
public class PatientContoller {

    @Autowired
    private IPatientService service;


    /**
     * New patientDto to add if already exist is not saved
     *
     * @param patientDto to save
     * @return patientDto when is create else return Error
     */
    @PostMapping("/add")
    public ResponseEntity<PatientDto> addPatient(@RequestBody @Valid PatientDto patientDto) {
        log.info("POST : patient/add ");
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.add(patientDto));
        } catch (PatientDtoAlreadyExistException patientDtoAlreadyExistException) {
            log.error("Post : add patient Already exist : {}", patientDtoAlreadyExistException.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, patientDtoAlreadyExistException.getMessage());
        }
    }

    /**
     * Send PatienttDto list.
     *
     * @return the patient List
     */
    @GetMapping("/all")
    public ResponseEntity<List<PatientDto>> allPatient() {
        log.info("GET : patient/ALL ");
        return ResponseEntity.status(HttpStatus.OK).body(service.readAll());
    }

    /**
     * PatientDto is update
     *
     * @param id         to update patient
     * @param patientDto the entity update
     * @return patientDto when is update
     */
    @PutMapping("/{id}")
    public ResponseEntity<PatientDto> updatePatient(@PathVariable("id") @NotBlank Integer id, @RequestBody @Valid PatientDto patientDto) {
        log.info("PUT : /patient/{}", id);
        PatientDto update;
        try {
            update = service.update(id, patientDto);
        } catch (PatientDtoNotFoundException patientDtoNotFoundException) {
            log.error("DELETE : /patient/{} - Not found : {}", patientDtoNotFoundException.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, patientDtoNotFoundException.getMessage());
        }
        log.info("PUT : /patient/{} - SUCCESS", id);
        return ResponseEntity.status(HttpStatus.OK).body(update);
    }


    /**
     * Find Patient by Id and delete the patient
     *
     * @param id to delete patient
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> patientDelete(@PathVariable("id") @NotBlank Integer id) {
        log.debug("DELETE : /patient/{}", id);
        try {
            service.delete(id);
        } catch (PatientDtoNotFoundException patientDtoNotFoundException) {
            log.error("DELETE : /patient/{} - Not found : {}", patientDtoNotFoundException.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, patientDtoNotFoundException.getMessage());
        }
        log.info("DELETE : /patient/{} - SUCCESS", id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
