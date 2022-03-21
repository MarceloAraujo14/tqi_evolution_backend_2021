package com.tqibank.validation.constraints;

import com.tqibank.validation.SenhaValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SenhaValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Senha {

    String message() default "A senha deve conter: no mínimo 6 caracteres, pelo menos uma letra maiúscula, pelo menos uma letra minúscula, pelo menos um número, pelo menos um caractere especial";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
