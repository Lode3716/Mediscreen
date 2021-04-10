package com.mediscreen.dto;

import com.googlecode.jmapper.JMapper;
import com.mediscreen.domain.Patient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigDto {

    @Bean
    JMapper<PatientDto, Patient> patientJMapper(){
        return new JMapper<>(PatientDto.class, Patient.class);
    }

    @Bean
    JMapper<Patient, PatientDto> patientUnJMapper() {
        return new JMapper<>(Patient.class,PatientDto.class);
    }
}
