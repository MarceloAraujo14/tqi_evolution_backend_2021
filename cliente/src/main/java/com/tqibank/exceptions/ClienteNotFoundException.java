package com.tqibank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClienteNotFoundException extends Exception {

    public ClienteNotFoundException(String rg) {
        super(String.format("Cliente com o RG %s não encontrado.", rg));
    }
}
