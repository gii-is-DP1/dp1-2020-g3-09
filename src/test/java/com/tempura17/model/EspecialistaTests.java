package com.tempura17.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class EspecialistaTests {

    private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}


    @Test
	void shouldNotValidateWhenDNIEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
        Especialista especialista = new Especialista();
        especialista.setDni("");
        especialista.setDireccion("casoplon");
        especialista.setTelefono("603245234");
        especialista.setCorreo("correo@gmail.com");
        especialista.setEspecialidad(Especialidad.ONCOLOGIA);
        especialista.setFirstName("especialista1");
        especialista.setLastName("apellidos");
		Validator validator = createValidator();
		Set<ConstraintViolation<Especialista>> constraintViolations = validator.validate(especialista);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Especialista> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("dni");
        assertThat(violation.getMessage()).isEqualTo("El DNI no puede estar vacío");
	}


    @Test
	void shouldNotValidateWhenDireccionEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
        Especialista especialista = new Especialista();
        especialista.setDni("80234567R");
        especialista.setDireccion("");
        especialista.setTelefono("603245234");
        especialista.setCorreo("correo@gmail.com");
        especialista.setEspecialidad(Especialidad.ONCOLOGIA);
        especialista.setFirstName("especialista1");
        especialista.setLastName("apellidos");
		Validator validator = createValidator();
		Set<ConstraintViolation<Especialista>> constraintViolations = validator.validate(especialista);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Especialista> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("direccion");
        assertThat(violation.getMessage()).isEqualTo("La dirección no puede estar vacía");
	}


    @Test
	void shouldNotValidateWhenTelefonoEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
        Especialista especialista = new Especialista();
        especialista.setDni("80234567R");
        especialista.setDireccion("casoplon");
        especialista.setTelefono("");
        especialista.setCorreo("correo@gmail.com");
        especialista.setEspecialidad(Especialidad.ONCOLOGIA);
        especialista.setFirstName("especialista1");
        especialista.setLastName("apellidos");
		Validator validator = createValidator();
		Set<ConstraintViolation<Especialista>> constraintViolations = validator.validate(especialista);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Especialista> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("telefono");
        assertThat(violation.getMessage()).isEqualTo("El teléfono no puede estar vacío");
	}


    @Test
	void shouldNotValidateWhenCorreoEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
        Especialista especialista = new Especialista();
        especialista.setDni("80234567R");
        especialista.setDireccion("casoplon");
        especialista.setTelefono("603245234");
        especialista.setCorreo("");
        especialista.setEspecialidad(Especialidad.ONCOLOGIA);
        especialista.setFirstName("especialista1");
        especialista.setLastName("apellidos");
		Validator validator = createValidator();
		Set<ConstraintViolation<Especialista>> constraintViolations = validator.validate(especialista);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Especialista> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("correo");
        assertThat(violation.getMessage()).isEqualTo("El correo no puede estar vacío");
	}
    
}
