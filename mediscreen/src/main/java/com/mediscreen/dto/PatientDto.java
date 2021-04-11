package com.mediscreen.dto;

import com.googlecode.jmapper.annotations.JGlobalMap;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@JGlobalMap
public class PatientDto {

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

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
}
