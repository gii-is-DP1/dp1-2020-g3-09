package com.tempura17.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


public class PersonTests {

    private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
    }
    

    @Test
	void shouldNotValidateWithEmptyfirstName() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
        Person person = new Person();
        person.setFirstName("");
        person.setLastName("lastName");

		Validator validator = createValidator();
		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("firstName");
		assertThat(violation.getMessage()).isEqualTo("must not be empty");
    }

    @Test
	void shouldNotValidateWithEmptylastName() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
        Person person = new Person();
        person.setFirstName("firstName");
        person.setLastName("");

		Validator validator = createValidator();
		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("lastName");
		assertThat(violation.getMessage()).isEqualTo("must not be empty");
    }
    
}
