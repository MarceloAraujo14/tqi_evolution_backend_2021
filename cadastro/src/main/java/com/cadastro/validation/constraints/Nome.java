package com.cadastro.validation.constraints;

import com.cadastro.validation.NomeValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NomeValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Nome {

    String message() default "O nome deve ser composto de nome e sobrenome e conter apenas de letras.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
