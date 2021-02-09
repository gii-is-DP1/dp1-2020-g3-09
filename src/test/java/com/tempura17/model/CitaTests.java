package com.tempura17.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class CitaTests {



    private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	/* Validaciones obsoletas
	@Test
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
	void shouldNotValidateEspecialidad(){
		Cita cita = new Cita();
		Formato formato = Formato.ONLINE;
		cita.setFormato(formato);
		Especialidad noFactible = Especialidad.ENDOCRINOLOGIA;
		cita.setEspecialidad(noFactible);
		Tipologia tipologia = Tipologia.PRIVADO;
		cita.setTipo(tipologia);
		cita.setPaciente(mock(Paciente.class));
		cita.setEspecialista(mock(Especialista.class));

		Validator validator = createValidator();
		Set<ConstraintViolation<Cita>> constraintViolations = validator.validate(cita);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Cita> violation = constraintViolations.iterator().next();
		String r1 = "Debido a que la cita no es presencial, no es posible seleccionar la especialidad deseada";
		assertThat(violation.getMessage()).isEqualTo(r1);

	}

	@Test
	void ShouldNotValidateWithNullTipo() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Cita cita = new Cita();
		cita.setFormato(Formato.PRESENCIAL);
		cita.setTipo(null);
		Paciente paciente = mock(Paciente.class);
		cita.setPaciente(paciente);
		Especialista especialista = mock(Especialista.class);
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
		cita.setTipo(Tipologia.ASEGURADO);
		cita.setFormato(null);
		Paciente paciente = mock(Paciente.class);
		cita.setPaciente(paciente);
		Especialista especialista = mock(Especialista.class);
		cita.setEspecialista(especialista);		

		Validator validator = createValidator();
		Set<ConstraintViolation<Cita>> constraintViolations = validator.validate(cita);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Cita> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("formato");
		assertThat(violation.getMessage()).isEqualTo("El formato no puede ser nulo");
	}
    
}
