package com.mediscreen.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.googlecode.jmapper.annotations.JGlobalMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JGlobalMap
public class PatientDto {

    private Integer id;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @JsonFormat(pattern="yyyy-MM-dd")
    @JsonSerialize(using= LocalDateSerializer.class)
    @JsonDeserialize(using= LocalDateDeserializer.class)
    @NotNull(message = "Date of birthday is mandatory")
    private LocalDate dob;
    /**
     * Gender of person
     * M = man
     * F = women
     */
    @NotNull(message = "Gender is mandatory")
    private Character gender;

    private String address;

    private String phone;

    public PatientDto(String lastName, String firstName, LocalDate dob, Character gender, String address, String phone) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
    }

    public PatientDto(String lastName, String firstName, String phone) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.phone = phone;
    }
}
