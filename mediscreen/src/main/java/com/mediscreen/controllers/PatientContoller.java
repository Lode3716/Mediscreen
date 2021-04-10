package com.mediscreen.controllers;


import com.mediscreen.dto.PatientDto;
import com.mediscreen.services.IPatientService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Log4j2
@RequestMapping(value ="patient")
@Controller
public class PatientContoller {

    @Autowired
    private IPatientService service;


    /**
     * New patientDto to add
     *
     * @param patientDto to save
     * @return patientDto when is create
     */
    @PostMapping("/add")
    public ResponseEntity<PatientDto> addPatient(@RequestBody @Valid PatientDto patientDto) {
        log.info("POST : patient/add "+patientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(patientDto));
    }

}
