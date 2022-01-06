package com.cadastro.validation;


import com.cadastro.validation.constraints.RG;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RGValidation implements ConstraintValidator<RG, String> {
    @Override
    public void initialize(RG constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        String rg = s == null ? "" : s;
        return rg.matches("(^\\d{1,2}).?(\\d{3}).?(\\d{3})-?(\\d{1}|X|x$)");
    }
}
