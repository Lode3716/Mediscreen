package com.mediscreen.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.domain.Patient;
import com.mediscreen.dto.PatientDto;
import com.mediscreen.repositories.IPatientRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class PatientContollerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private IPatientRepository repository;

    private static PatientDto patientDto;
    private static Patient patient;

    private static PatientDto patientDtoFail;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        patientDto = new PatientDto("Gerard", "Corbien", LocalDate.of(1966, 12, 31), false, "Au club Dorothee 75000 Paris", "400-555-6666");
        patientDtoFail = new PatientDto("", "", LocalDate.of(1966, 12, 31), false, "", "400-555-6666");
        patient = new Patient(patientDto.getLastName(), patientDto.getFirstName(), patientDto.getDob(), patientDto.getSex(), patientDto.getAddress(), patientDto.getPhone());

    }


    @AfterEach
    public void delete() {
        Optional.ofNullable(repository.findByLastNameAndFirstNameAndDob(patientDto.getLastName(), patientDto.getFirstName(), patientDto.getDob()))
                .map(Patient::getId)
                .ifPresent(deletePatient -> repository.deleteById(deletePatient));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Tag("PatientAdd")
    @DisplayName("Given a patientDto then save patient return patient with code 200")
    public void givenPatientDtoAdd_whenPostRequestSuccess_thenPatientAdd() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/patient/add")
                .content(asJsonString(patientDto))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.lastName").value(patientDto.getLastName()))
                .andExpect(jsonPath("$.firstName").value(patientDto.getFirstName()))
                .andExpect(jsonPath("$.dob").value(patientDto.getDob().toString()))
                .andExpect(result -> assertNotNull(jsonPath("$.id")));
    }

    @Test
    @Tag("PatientAdd")
    @DisplayName("Given a patientDto are not valid return error")
    public void givenPatientDtoAdd_whenPostRequestFail_thenPatientNotAdd() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/patient/add")
                .content(asJsonString(patientDtoFail))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("Adress is mandatory")
                        && result.getResolvedException().getMessage().contains("First name is mandatory")
                        && result.getResolvedException().getMessage().contains("Last name is mandatory")));
    }

    @Test
    @Tag("PatientAdd")
    @DisplayName("Given a patientDto already exiting return error")
    public void givenPatientDtoExistAdd_whenPostRequestFail_thenPatientNotAdd() throws Exception {
        repository.save(patient);
        mvc.perform(MockMvcRequestBuilders.post("/patient/add")
                .content(asJsonString(patientDto))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("The patient with the information entered already exists")));
    }
}