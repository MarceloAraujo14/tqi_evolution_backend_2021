package com.validation;

import com.cadastro.repository.CadastroRepository;
import com.validation.constraints.DuplicatedRG;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DuplicatedRGValidation implements ConstraintValidator<DuplicatedRG, String> {
    @Autowired
    CadastroRepository cadastroRepository;

    @Override
    public void initialize(DuplicatedRG constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String rg, ConstraintValidatorContext constraintValidatorContext) {
        return (cadastroRepository.findByRg(rg).isEmpty());
    }
}
