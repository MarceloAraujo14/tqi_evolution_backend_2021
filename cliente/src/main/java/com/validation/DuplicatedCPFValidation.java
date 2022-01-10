package com.validation;

import com.repository.ClienteRepository;
import com.validation.constraints.DuplicatedCPF;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DuplicatedCPFValidation implements ConstraintValidator<DuplicatedCPF, String> {
    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public void initialize(DuplicatedCPF constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext constraintValidatorContext) {

        return (cpf == null || cpf.isEmpty() || clienteRepository.findByCpf(cpf).isEmpty());
    }

}
