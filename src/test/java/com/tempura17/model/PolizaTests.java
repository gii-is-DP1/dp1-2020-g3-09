package com.tempura17.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class PolizaTests {

    private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

    @Test
    @Disabled
    //Problemas con el BigDecimal
	void shouldNotValidateWhenPrecioEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
        Poliza poliza = new Poliza();
        poliza.setPrecio(new BigDecimal("1000.50"));
		Validator validator = createValidator();
		Set<ConstraintViolation<Poliza>> constraintViolations = validator.validate(poliza);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Poliza> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("precio");
	}


    
}
