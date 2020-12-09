package com.tempura17.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


class CalculadoraTests{

	

    private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}


    @Test
	void shouldNotValidateWhenPesoEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
        CalculadoraSalud calculadora = new CalculadoraSalud();
		calculadora.setAltura("1.85");
		Validator validator = createValidator();
		Set<ConstraintViolation<CalculadoraSalud>> constraintViolations = validator.validate(calculadora);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<CalculadoraSalud> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("peso");

	}
}

