package com.tempura17.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class TratamientoTests {

    private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	@Disabled
	void shouldValidate(){
		Tratamiento tratamiento = new Tratamiento();
		String descripcion = generateRandom(30);
		tratamiento.setDescripcion(descripcion);
		tratamiento.setDuracion(3);
		Poliza poliza = mock(Poliza.class);
        tratamiento.setPoliza(poliza);
        Acta acta = new Acta();
		acta.setDescripcion(generateRandom(30));
		acta.setDiagnostico(generateRandom(30));
		acta.setExploracion(generateRandom(30));
		tratamiento.setActa(acta);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Acta>> constraintViolations = validator.validate(acta);

		assertThat(constraintViolations).isEqualTo(new HashSet<>());
	}
	
	@Test
	void shouldNotValidateWithMinDescripcion() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Tratamiento tratamiento = new Tratamiento();
		tratamiento.setDescripcion("1");
		tratamiento.setDuracion(1);
		Poliza poliza = mock(Poliza.class);
        tratamiento.setPoliza(poliza);
        Acta acta = mock(Acta.class);
        tratamiento.setActa(acta);

		Validator validator = createValidator();
		Set<ConstraintViolation<Tratamiento>> constraintViolations = validator.validate(tratamiento);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Tratamiento> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("descripcion");
		assertThat(violation.getMessage()).isEqualTo("La descripción tiene que tener un tamaño mínimo de 10 y máximo de 300 carácteres");
    }
	
	@Test
	void shouldNotValidateWithMMaxDescripcion() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Tratamiento tratamiento = new Tratamiento();
		String descripcion = generateRandom(301);
		tratamiento.setDescripcion(descripcion);
		tratamiento.setDuracion(1);
		Poliza poliza = mock(Poliza.class);
        tratamiento.setPoliza(poliza);
        Acta acta = mock(Acta.class);
        tratamiento.setActa(acta);

		Validator validator = createValidator();
		Set<ConstraintViolation<Tratamiento>> constraintViolations = validator.validate(tratamiento);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Tratamiento> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("descripcion");
		assertThat(violation.getMessage()).isEqualTo("La descripción tiene que tener un tamaño mínimo de 10 y máximo de 300 carácteres");
	}

	@Test
	void shouldNotValidateWithNoDuracion() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Tratamiento tratamiento = new Tratamiento();
		String descripcion = generateRandom(50);
		tratamiento.setDescripcion(descripcion);
		tratamiento.setDuracion(0);
		Poliza poliza = mock(Poliza.class);
        tratamiento.setPoliza(poliza);
        Acta acta = mock(Acta.class);
        tratamiento.setActa(acta);

		Validator validator = createValidator();
		Set<ConstraintViolation<Tratamiento>> constraintViolations = validator.validate(tratamiento);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Tratamiento> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("duracion");
		assertThat(violation.getMessage()).isEqualTo("la duración no puede ser menor de 1 día");
	}

	/* Validaciones obsoletas
	@Test
	@Disabled
	void shouldNotValidateWithNullPoliza() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
        Tratamiento tratamiento = new Tratamiento();
		String descripcion = generateRandom(20);
		tratamiento.setDescripcion(descripcion);
		tratamiento.setDuracion(3);
        tratamiento.setPoliza(null);
        Acta acta = mock(Acta.class);
        tratamiento.setActa(acta);

		Validator validator = createValidator();
		Set<ConstraintViolation<Tratamiento>> constraintViolations = validator.validate(tratamiento);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Tratamiento> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("poliza");
		assertThat(violation.getMessage()).isEqualTo("La poliza no puede ser nula");
    }
    
	@Test
	@Disabled
	void shouldNotValidateWithNullActa() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Tratamiento tratamiento = new Tratamiento();
		String descripcion = generateRandom(50);
		tratamiento.setDescripcion(descripcion);
		tratamiento.setDuracion(3);
		Poliza poliza = mock(Poliza.class);
        tratamiento.setPoliza(poliza);
        tratamiento.setActa(null);

		Validator validator = createValidator();
		Set<ConstraintViolation<Tratamiento>> constraintViolations = validator.validate(tratamiento);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Tratamiento> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("acta");
		assertThat(violation.getMessage()).isEqualTo("El acta no puede ser nulo");
	}*/
	
    
	public String generateRandom(Integer length){
		// Creación de un string aleatorio para una longitud determinada
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		// La descripción tiene que tener un tamaño mínimo de 10 y máximo de 300 carácteres
		int targetStringLength = length;
		Random random = new Random();
		String randomString = random.ints(leftLimit, rightLimit + 1)
		  .limit(targetStringLength)
		  .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
		  .toString();

		return randomString;
	}
    
}
