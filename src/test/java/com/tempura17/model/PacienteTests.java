package com.tempura17.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * @author Michael Isvy Simple test to make sure that Bean Validation is working (useful
 * when upgrading to a new version of Hibernate Validator/ Bean Validation)
 */
class PacienteTests {

    /*
    @Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private EmployeeRepository employeeRepository;

    */
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateWhenDNIEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Paciente paciente = new Paciente();
		paciente.setDni("");
		paciente.setFirstName("rodrigo");
		paciente.setLastName("garcia");

		Validator validator = createValidator();
		Set<ConstraintViolation<Paciente>> constraintViolations = validator.validate(paciente);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Paciente> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("dni");
		assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}

	@Test
	void shouldNotValidateWhenEmailIsNotEmail() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Paciente paciente = new Paciente();
		paciente.setDni("25000000A");
		paciente.setFirstName("rodrigo");
		paciente.setLastName("garcia");
		paciente.setEmail("noEmail");

		Validator validator = createValidator();
		Set<ConstraintViolation<Paciente>> constraintViolations = validator.validate(paciente);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Paciente> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("email");
		assertThat(violation.getMessage()).isEqualTo("must be a well-formed email address");
	}

}
