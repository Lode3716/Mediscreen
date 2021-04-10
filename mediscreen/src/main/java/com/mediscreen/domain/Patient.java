package com.mediscreen.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
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
     * kind of person
     */
    @Column(name = "sex")
    private Boolean sex;

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
}
