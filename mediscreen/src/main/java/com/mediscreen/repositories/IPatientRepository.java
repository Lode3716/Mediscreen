package com.mediscreen.repositories;

import com.mediscreen.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IPatientRepository extends JpaRepository<Patient, Integer> {
    /**
     * Check in bdd if the patient is already exiting
     * @param lastName  last name
     * @param firstName first name
     * @param dob Date of birthday
     * @return true if exist else false
     */
    Patient findByLastNameAndFirstNameAndDob(String lastName, String firstName, LocalDate dob);

    /**
     * Find in bdd if the word contains lastname patient
     * @param lastName  last name
     * @return List<Patient>
     */
    List<Patient> findByLastNameContaining(String lastName);
}
