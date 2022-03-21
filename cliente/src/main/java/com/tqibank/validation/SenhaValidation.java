package com.tqibank.validation;

import com.tqibank.validation.constraints.Senha;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SenhaValidation implements ConstraintValidator<Senha, String> {

    @Override
    public void initialize(Senha constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        String senha = s == null ? "" : s;
        return senha.matches("(?=^.{6,}$)((?=.*\\w)(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[|!\"$%&\\/\\(\\)\\?\\^\\'\\\\\\+\\-\\*]))^.*");
    }
}
