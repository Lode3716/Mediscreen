package com.mediscreen.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.domain.Patient;
import com.mediscreen.dto.PatientDto;
import com.mediscreen.repositories.IPatientRepository;
import lombok.extern.log4j.Log4j2;
import org.hamcrest.Matchers;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
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
    private static PatientDto patientDtoUpdate;

    private static Patient patient;
    private static Patient patientUpdate;

    private static PatientDto patientDtoFail;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();

        patientDto = new PatientDto("Gerard", "Corbien", LocalDate.of(1966, 12, 31), 'M', "Au club Dorothee 75000 Paris", "400-555-6666");
        patientDtoUpdate= new PatientDto("Benard", "Roger", LocalDate.of(1980, 12, 31), 'M', "Au club Dorothee 75550 Paris", "400-556-6666");

        patientDtoFail = new PatientDto("", "","400-555-6666");

        patient = new Patient(patientDto.getLastName(), patientDto.getFirstName(), patientDto.getDob(), patientDto.getGender(), patientDto.getAddress(), patientDto.getPhone());
        patientUpdate=new Patient("Benard", "Roger", LocalDate.of(1980, 12, 31), 'F', "Au club Dorothee 75550 Paris", "400-556-6666");
    }


    @AfterEach
    public void delete() {
        Optional.ofNullable(repository.findByLastNameAndFirstNameAndDob(patientDto.getLastName(), patientDto.getFirstName(), patientDto.getDob()))
                .map(Patient::getId)
                .ifPresent(deletePatient -> repository.deleteById(deletePatient));

        Optional.ofNullable(repository.findByLastNameAndFirstNameAndDob(patientDtoUpdate.getLastName(), patientDtoUpdate.getFirstName(), patientDtoUpdate.getDob()))
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
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("Gender is mandatory")
                        && result.getResolvedException().getMessage().contains("Date of birthday is mandatory")
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

    @Test
    @Tag("PatientUpdate")
    @DisplayName("Given a patientDto update in BDD and test the update patient - sucess")
    public void givenPatientDtoUpdate_whenPutRequestSucess_thenPatientUpdate() throws Exception {
        Patient savePatient=repository.save(patient);
        String url="/patient/".concat(String.valueOf(savePatient.getId()));
                mvc.perform(MockMvcRequestBuilders.put(url)
                .content(asJsonString(patientDtoUpdate))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value(patientDtoUpdate.getLastName()))
                .andExpect(jsonPath("$.firstName").value(patientDtoUpdate.getFirstName()))
                .andExpect(jsonPath("$.dob").value(patientDtoUpdate.getDob().toString()))
                .andExpect(jsonPath("$.id").value(savePatient.getId()));
    }

    @Test
    @Tag("PatientUpdate")
    @DisplayName("Given a patientDto update but id is not Found return PatientDtoNotFoundException - Fail")
    public void givenPatientDtoUpdate_whenPutRequestFailNotFoundId_thenPatientNotUpdate() throws Exception {
        repository.save(patient);
        String url="/patient/".concat(String.valueOf(15000));
        mvc.perform(MockMvcRequestBuilders.put(url)
                .content(asJsonString(patientDto))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("There is no patient with this id ")));
    }


    @Test
    @Tag("PatientAll")
    @DisplayName("Given a save two patients in bdd,check if list return equals 2 patients")
    public void givenPatientDtoAll_whenGETRequestSucess_thenPatientListPatient() throws Exception {
         repository.save(patient);
         repository.save(patientUpdate);

        mvc.perform(MockMvcRequestBuilders.get("/patient/all")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    @Tag("PatientDelete")
    @DisplayName("Given a save two patients in bdd,check if list return equals 2 patients")
    public void givenPatientDtoDelete_whenGETRequestSucess_thenPatientIsDelete() throws Exception {
        Patient save=repository.save(patient);

        String url="/patient/".concat(String.valueOf(save.getId()));
        mvc.perform(MockMvcRequestBuilders.delete(url)
                .accept(APPLICATION_JSON))
                .andExpect(status().isAccepted());
        assumeFalse(repository.existsById(save.getId()));
    }

    @Test
    @Tag("PatientDelete")
    @DisplayName("Given id of patient Delete but id not Found")
    public void givenPatientDtoDelete_whenGETRequestFailNotFoundId_thenPatientIsNotDelete() throws Exception {
        String url="/patient/".concat(String.valueOf(1231));
        mvc.perform(MockMvcRequestBuilders.delete(url)
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("There is no patient with this id ")));
    }

    @Test
    @Tag("PatientByID")
    @DisplayName("Given a save patient in bdd,check if list return equals 2 patients")
    public void givenPatientById_whenGETRequestSucess_thenReturnPatientFind() throws Exception {
        Patient save=repository.save(patient);

        String url="/patient/".concat(String.valueOf(save.getId()));

        mvc.perform(MockMvcRequestBuilders.get(url)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value(patient.getLastName()))
                .andExpect(jsonPath("$.firstName").value(patient.getFirstName()))
                .andExpect(jsonPath("$.dob").value(patient.getDob().toString()))
                .andExpect(jsonPath("$.id").value(patient.getId()))
                .andExpect(jsonPath("$.address").value(patient.getAddress()))
                .andExpect(jsonPath("$.phone").value(patient.getPhone()));
    }

    @Test
    @Tag("PatientByID")
    @DisplayName("Given a save patient in bdd,check if list return equals 2 patients")
    public void givenPatientById_whenGETRequestFail_thenReturnPatientNotFound() throws Exception {

        String url="/patient/".concat(String.valueOf(12345));

        mvc.perform(MockMvcRequestBuilders.get(url)
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("There is no patient with this id ")));
    }


    @Test
    @Tag("PatientByName")
    @DisplayName("Given a save patients in bdd,find by name retunr 1 patient")
    public void givenPatientByName_whenGETRequestSucess_thenReturnPatientFind() throws Exception {
        repository.save(patient);
        repository.save(patientUpdate);

        mvc.perform(MockMvcRequestBuilders.get("/patient")
                .queryParam("name_like", "Gerard")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }
}
