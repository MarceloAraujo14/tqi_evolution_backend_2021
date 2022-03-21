package com.tqibank.validation;

import com.tqibank.cliente.ClienteRepository;
import com.tqibank.validation.constraints.DuplicatedRG;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DuplicatedRGValidation implements ConstraintValidator<DuplicatedRG, String> {
    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public void initialize(DuplicatedRG constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String rg, ConstraintValidatorContext constraintValidatorContext) {
        return (clienteRepository.findByRg(rg).isEmpty());
    }
}
