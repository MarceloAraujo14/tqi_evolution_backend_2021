package com.tqibank.validation.constraints;

import com.tqibank.validation.RGValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RGValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RG {

    String message() default "RG inválido.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
