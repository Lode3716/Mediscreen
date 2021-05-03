package com.mediscreen.services;

import com.googlecode.jmapper.JMapper;
import com.mediscreen.domain.Patient;
import com.mediscreen.dto.PatientDto;
import com.mediscreen.repositories.IPatientRepository;
import com.mediscreen.services.exceptions.PatientDtoAlreadyExistException;
import com.mediscreen.services.exceptions.PatientDtoNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        if(existByPatientInformation(patientDto.getLastName(),patientDto.getFirstName(),patientDto.getDob()))
        {
            log.error("Service add not saved because : The patient with the information entered already exists");
            throw new PatientDtoAlreadyExistException("The patient with the information entered already exists");
        }else{
            Patient patient = repository.save(patientUnJMapper.getDestination(patientDto));
            log.info("Genre: "+patientDto.getGender());
            log.info("Service : Patient is save in Bdd : {} ", patient.getId());
            return patientJMapper.getDestination(patient);
        }
    }

    /**
     * Find list Patient and Convert PatientDto
     * @return the list of PatientDto
     */
    @Override
    public List<PatientDto> readAll() {
        List<PatientDto> patientDtos = new ArrayList<>();
        repository.findAll()
                .forEach(patient ->
                {
                    patientDtos.add(patientJMapper.getDestination(patient));
                });
        log.info("Service : read list Patients : {} ", patientDtos.size());
        return patientDtos;
    }

    /**
     * Check id exist, if valid update Patient
     * @param id
     * @param patientDto to update
     * @return the Patient update and converted the PatientDto
     */
    @Override
    public PatientDto update(Integer id, PatientDto patientDto) {
        Patient updatePatient = existById(id);
        updatePatient.setFirstName(patientDto.getFirstName());
        updatePatient.setLastName(patientDto.getLastName());
        updatePatient.setAddress(patientDto.getAddress());
        updatePatient.setGender(patientDto.getGender());
        updatePatient.setPhone(patientDto.getPhone());
        updatePatient.setDob(patientDto.getDob());
        log.info("Service : update Patient : {} ", updatePatient.getId());
        return patientJMapper.getDestination(repository.save(updatePatient));
    }


    /**
     * Check id exist, if valid delete patient
     * @param id to delete
     */
    @Override
    public void delete(Integer id) {
        repository.deleteById(existById(id).getId());
        log.info("Service : delete patient id : {}", id);
    }

    /**
     * Check id exist, if valid return patient
     * @param id to patient
     */
    @Override
    public PatientDto readById(Integer id) {
        return  patientJMapper.getDestination(existById(id));
    }

    @Override
    public Patient existById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new PatientDtoNotFoundException("There is no patient with this id " + id));
    }

    @Override
    public Boolean existByPatientInformation(String lastName, String firstName, LocalDate dob) {
        return Optional.ofNullable(repository.findByLastNameAndFirstNameAndDob(lastName,firstName,dob))
                .isPresent();
    }

    @Override
    public List<PatientDto> getPatientByLastName(String lastName) {
        List<PatientDto> patientDtos = new ArrayList<>();
        repository.findByLastNameContaining(lastName)
                .forEach(patient ->
                {
                    patientDtos.add(patientJMapper.getDestination(patient));
                });
        log.info("Service : read patients by last name: {} ", patientDtos.size());
        return patientDtos;
    }

}
