package com.mediscreen.services;

import com.googlecode.jmapper.JMapper;
import com.mediscreen.domain.Patient;
import com.mediscreen.dto.PatientDto;
import com.mediscreen.repositories.IPatientRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class PatientServiceImp implements IPatientService {

    @Autowired
    IPatientRepository repository;

    @Autowired
    JMapper<PatientDto, Patient> patientJMapper;

    @Autowired
    JMapper<Patient, PatientDto> patientUnJMapper;


    @Override
    public PatientDto add(PatientDto patientDto) {
        Patient patient = repository.save(patientUnJMapper.getDestination(patientDto));
        log.info("Service : BidList is save in Bdd : {} ", patient.getId());
        return patientJMapper.getDestination(patient);
    }

    @Override
    public List<PatientDto> readAll() {
        return null;
    }

    @Override
    public PatientDto update(Integer id, PatientDto objet) {
        return null;
    }


    @Override
    public void delete(Integer id) {

    }
}
