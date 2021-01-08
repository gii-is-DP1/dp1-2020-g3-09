package com.tempura17.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.tempura17.service.EspecialistaService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class CitaTests {

    private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	/*@Test
	void ShouldNotValidateWithNullPaciente() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Cita cita = new Cita();
		cita.setFormato(Formato.ONLINE);
		cita.setTipo(Tipologia.ASEGURADO);

		Especialista especialista = new Especialista();
		especialista.setFirstName("firstName");
		especialista.setLastName("lastName");
		cita.setEspecialista(especialista);

		cita.setPaciente(null);

		Validator validator = createValidator();
		Set<ConstraintViolation<Cita>> constraintViolations = validator.validate(cita);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Cita> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("paciente");
		assertThat(violation.getMessage()).isEqualTo("El paciente no puede ser nulo");

		Paciente paciente = new Paciente();
		paciente.setDni("");
		paciente.setFirstName("rodrigo");
		paciente.setLastName("garcia");
	}

	@Test
	void ShouldNotValidateWithNullEspecialista() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Cita cita = new Cita();
		cita.setFormato(Formato.ONLINE);
		cita.setTipo(Tipologia.ASEGURADO);

		Paciente paciente = new Paciente();
		paciente.setDni("");
		paciente.setFirstName("rodrigo");
		paciente.setLastName("garcia");
		cita.setPaciente(paciente);

		cita.setEspecialista(null);

		Validator validator = createValidator();
		Set<ConstraintViolation<Cita>> constraintViolations = validator.validate(cita);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Cita> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("especialista");
		assertThat(violation.getMessage()).isEqualTo("El especialista no puede ser nulo");
	}
	*/
	@Test
	void ShouldNotValidateWithNullTipo() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Cita cita = new Cita();
		cita.setFormato(Formato.ONLINE);
		cita.setTipo(null);

		Paciente paciente = new Paciente();
		paciente.setDni("");
		paciente.setFirstName("rodrigo");
		paciente.setLastName("garcia");
		cita.setPaciente(paciente);

		Especialista especialista = new Especialista();
		especialista.setFirstName("firstName");
		especialista.setLastName("lastName");
		cita.setEspecialista(especialista);		

		Validator validator = createValidator();
		Set<ConstraintViolation<Cita>> constraintViolations = validator.validate(cita);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Cita> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("tipo");
		assertThat(violation.getMessage()).isEqualTo("La tipología no puede ser nula");
	}

	@Test
	void ShouldNotValidateWithNullFormato() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Cita cita = new Cita();
		cita.setFormato(null);
		cita.setTipo(Tipologia.ASEGURADO);

		Paciente paciente = new Paciente();
		paciente.setDni("");
		paciente.setFirstName("rodrigo");
		paciente.setLastName("garcia");
		cita.setPaciente(paciente);

		Especialista especialista = new Especialista();
		especialista.setFirstName("firstName");
		especialista.setLastName("lastName");
		cita.setEspecialista(especialista);		

		Validator validator = createValidator();
		Set<ConstraintViolation<Cita>> constraintViolations = validator.validate(cita);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Cita> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("formato");
		assertThat(violation.getMessage()).isEqualTo("El formato no puede ser nulo");
	}
    
}
