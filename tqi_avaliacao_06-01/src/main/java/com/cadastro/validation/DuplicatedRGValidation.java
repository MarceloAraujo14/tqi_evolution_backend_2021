package com.cadastro.validation;

import com.cadastro.cliente.repository.ClienteRepository;
import com.cadastro.validation.constraints.DuplicatedRG;
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
        return (rg == null || rg.isEmpty() || clienteRepository.findByRg(rg).isEmpty());
    }
}
