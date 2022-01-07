package com.validation;

import com.cadastro.repository.ClienteRepository;
import com.validation.constraints.DuplicatedEmail;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DuplicatedEmailValidation implements ConstraintValidator<DuplicatedEmail, String> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(DuplicatedEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {

        return (email == null || email.isEmpty() || clienteRepository.findByEmail(email).getEmail().isEmpty());
    }
}
