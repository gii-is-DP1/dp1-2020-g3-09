package com.tempura17.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class AseguradoraTests {

    private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

    @Test
	void shouldNotValidateWhenNombreEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
        Aseguradora aseguradora = new Aseguradora();
		Validator validator = createValidator();
		Set<ConstraintViolation<Aseguradora>> constraintViolations = validator.validate(aseguradora);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Aseguradora> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("nombre");
        assertThat(violation.getMessage()).isEqualTo("El nombre no puede estar vacio");
	}
    
}
