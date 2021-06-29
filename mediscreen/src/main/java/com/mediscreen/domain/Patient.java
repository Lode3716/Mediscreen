package com.mediscreen.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "patient")
public class Patient implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Last Name
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Fist Name
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Date of Birthday
     */
    @Column(name = "dob")
    private LocalDate dob;

    /**
     * Gender of person
     * True = man
     * False = women
     */
    @Column(name = "gender")
    private Character gender;

    /**
     * Patient address
     */
    @Column(name = "address")
    private String address;

    /**
     * patient phone
     */
    @Column(name = "phone")
    private String phone;

    public Patient(String lastName, String firstName, LocalDate dob, Character gender, String address, String phone) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
    }
}
