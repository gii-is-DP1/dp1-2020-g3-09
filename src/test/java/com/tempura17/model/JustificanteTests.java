package com.tempura17.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class JustificanteTests {

    private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

    @Test
	void shouldNotValidateWhenMotivoNull() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
        Justificante justificante = new Justificante();
        justificante.setMotivo(null);
		Validator validator = createValidator();
		Set<ConstraintViolation<Justificante>> constraintViolations = validator.validate(justificante);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Justificante> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("motivo");
        assertThat(violation.getMessage()).isEqualTo("El motivo no puede estar vacio");
	}
    
}
