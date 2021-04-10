package com.mediscreen.services;

import com.mediscreen.domain.Patient;
import com.mediscreen.dto.PatientDto;

/**
 * Interface containing all the Patient's methods
 */
public interface IPatientService extends ICrudService<PatientDto> {


    Patient existById(Integer id);
}
