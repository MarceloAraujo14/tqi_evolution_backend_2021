package com.tqibank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicatedEmailException extends Exception {

    public DuplicatedEmailException(String email) {
        super(String.format("Email %s já está cadastrado.", email));
    }
}
