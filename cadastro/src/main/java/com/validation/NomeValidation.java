package com.validation;

import com.validation.constraints.Nome;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NomeValidation implements ConstraintValidator<Nome, String> {
    @Override
    public void initialize(Nome constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        String nome = s == null ? "" : s;
        return nome.matches("^((\\b[A-zÀ-ú']{2,40}\\b)\\s*){2,}$");
    }
}
