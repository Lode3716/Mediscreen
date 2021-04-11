package com.mediscreen.services;

import com.mediscreen.domain.Patient;
import com.mediscreen.dto.PatientDto;

import java.time.LocalDate;

/**
 * Interface containing all the Patient's methods
 */
public interface IPatientService extends ICrudService<PatientDto> {


    /**
     * Find Patient By id
     * @param id
     * @return the patient find or issue PatientDtoNotFoundException
     */
    Patient existById(Integer id);

    /**
     * Check patient is already exiting
     * @param lastName Last name
     * @param firstName Fisrt name
     * @param dob Date of birthday
     * @return true if exist
     */
    Boolean existByPatientInformation(String lastName, String firstName, LocalDate dob);
}
