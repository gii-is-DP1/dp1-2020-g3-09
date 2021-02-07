package com.tempura17.service.businessrules;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PossiblePolizaValidator.class})
public @interface ValidatePossiblePoliza {

    String message() default "Solicitadas demasiadas citas dentro del mismo mes para la poliza actualmente contrada";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
}
