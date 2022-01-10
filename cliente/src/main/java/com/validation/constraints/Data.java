package com.validation.constraints;

import com.validation.DataValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
@Documented
@Constraint(validatedBy = DataValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Data {



    String message() default "A data da primeira parcela deve ser até 3 meses da data de solicitação.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
