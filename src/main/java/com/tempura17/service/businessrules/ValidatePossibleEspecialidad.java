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
@Constraint(validatedBy = {PossibleEspecialidadValidator.class})
public @interface ValidatePossibleEspecialidad {
	String message() default "Debido a que la cita no es presencial, no es posible seleccionar la especialidad deseada";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
