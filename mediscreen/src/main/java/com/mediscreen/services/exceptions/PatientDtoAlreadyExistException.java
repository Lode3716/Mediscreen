package com.mediscreen.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PatientDtoAlreadyExistException extends RuntimeException {

    public PatientDtoAlreadyExistException() {
    }

    public PatientDtoAlreadyExistException(String message) {
        super(message);
    }
}
