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
    private LocalDate dob;
    /**
     * kind of person
     * True = man
     * False = women
     */
    private Boolean sex;

    @NotBlank(message = "Adress is mandatory")
    private String address;

    @NotBlank(message = "Phone is required")
    private String phone;

    public PatientDto(String lastName, String firstName, LocalDate dob, Boolean sex, String address, String phone) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.dob = dob;
        this.sex = sex;
        this.address = address;
        this.phone = phone;
    }
}
