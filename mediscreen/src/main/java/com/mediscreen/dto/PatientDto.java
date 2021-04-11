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
     * kind of person
     * True = man
     * False = women
     */
    @NotNull(message = "Kind is mandatory")
    private Boolean kind;

    private String address;

    private String phone;

    public PatientDto(String lastName, String firstName, LocalDate dob, Boolean kind, String address, String phone) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.dob = dob;
        this.kind = kind;
        this.address = address;
        this.phone = phone;
    }

    public PatientDto(String lastName, String firstName, String phone) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.phone = phone;
    }
}
