package com.mediscreen.services.exceptions;

public class PatientDtoNotFoundException extends RuntimeException {

    public PatientDtoNotFoundException() {
    }

    public PatientDtoNotFoundException(String message) {
        super(message);
    }
}
