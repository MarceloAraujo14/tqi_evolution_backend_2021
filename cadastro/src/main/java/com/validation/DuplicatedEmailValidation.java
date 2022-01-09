package com.validation;

import com.cadastro.repository.CadastroRepository;
import com.validation.constraints.DuplicatedEmail;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DuplicatedEmailValidation implements ConstraintValidator<DuplicatedEmail, String> {

    @Autowired
    private CadastroRepository cadastroRepository;

    @Override
    public void initialize(DuplicatedEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {

        return (email == null || email.isEmpty() || cadastroRepository.findByEmail(email).getEmail().isEmpty());
    }
}
