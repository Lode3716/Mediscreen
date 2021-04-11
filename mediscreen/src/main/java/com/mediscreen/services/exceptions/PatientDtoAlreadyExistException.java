package com.mediscreen.services.exceptions;

import com.mediscreen.dto.PatientDto;

public class PatientDtoAlreadyExistException extends RuntimeException {

    public PatientDtoAlreadyExistException() {
    }

    public PatientDtoAlreadyExistException(String message) {
        super(message);
    }
}
