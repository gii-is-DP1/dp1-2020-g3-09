package com.tempura17.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Locale;
import java.util.Random;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ActaTests {

	private Acta acta;

    private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
    }


	@BeforeEach
	void setup(){
		LocaleContextHolder.setLocale(Locale.ENGLISH);
        acta = new Acta();
        Especialista especialista = mock(Especialista.class);
		Cita cita = mock(Cita.class);
        acta.setEspecialista(especialista);
		acta.setCita(cita);
	}


	@Test
	@Disabled
    void shouldNotValidateWithNullEspecialista() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
        Cita cita = new Cita();
        Acta acta = new Acta();
        acta.setCita(cita);
        acta.setEspecialista(null);

		Validator validator = createValidator();
		Set<ConstraintViolation<Acta>> constraintViolations = validator.validate(acta);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Acta> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("especialista");
		assertThat(violation.getMessage()).isEqualTo("El especialista no puede ser nulo");
    }


	@Test
	@Disabled
    void shouldNotValidateWithNullCita() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
        Especialista especialista = new Especialista();
        Acta acta = new Acta();
        acta.setCita(null);
        acta.setEspecialista(especialista);

		Validator validator = createValidator();
		Set<ConstraintViolation<Acta>> constraintViolations = validator.validate(acta);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Acta> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("cita");
		assertThat(violation.getMessage()).isEqualTo("La cita no puede ser nula");
    }


    @Test
	void shouldNotValidateWithMinDescripcion() {


		acta.setExploracion(generateRandom(78));
        acta.setDiagnostico(generateRandom(59));
        acta.setDescripcion(generateRandom(3));

		Validator validator = createValidator();
		Set<ConstraintViolation<Acta>> constraintViolations = validator.validate(acta);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Acta> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("descripcion");
		assertThat(violation.getMessage()).isEqualTo("La descripción tiene que tener un tamaño mínimo de 5 y máximo de 300 carácteres");
    }
    
    @Test
	void shouldNotValidateWithMMaxDescripcion() {

		acta.setExploracion(generateRandom(78));
        acta.setDiagnostico(generateRandom(59));
        acta.setDescripcion(generateRandom(404));

		Validator validator = createValidator();
		Set<ConstraintViolation<Acta>> constraintViolations = validator.validate(acta);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Acta> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("descripcion");
		assertThat(violation.getMessage()).isEqualTo("La descripción tiene que tener un tamaño mínimo de 5 y máximo de 300 carácteres");
	}

	@Test
	void shouldNotValidateWithMinExploracion() {


		acta.setExploracion(generateRandom(2));
        acta.setDiagnostico(generateRandom(59));
        acta.setDescripcion(generateRandom(39));

		Validator validator = createValidator();
		Set<ConstraintViolation<Acta>> constraintViolations = validator.validate(acta);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Acta> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("exploracion");
		assertThat(violation.getMessage()).isEqualTo("La exploración tiene que tener un tamaño mínimo de 5 y máximo de 300 carácteres");
    }
    
    @Test
	void shouldNotValidateWithMMaxExploracion() {

		acta.setExploracion(generateRandom(875));
        acta.setDiagnostico(generateRandom(59));
        acta.setDescripcion(generateRandom(53));

		Validator validator = createValidator();
		Set<ConstraintViolation<Acta>> constraintViolations = validator.validate(acta);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Acta> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("exploracion");
		assertThat(violation.getMessage()).isEqualTo("La exploración tiene que tener un tamaño mínimo de 5 y máximo de 300 carácteres");
	}

	@Test
	void shouldNotValidateWithMinDiagnostico() {


		acta.setExploracion(generateRandom(78));
        acta.setDiagnostico(generateRandom(4));
        acta.setDescripcion(generateRandom(8));

		Validator validator = createValidator();
		Set<ConstraintViolation<Acta>> constraintViolations = validator.validate(acta);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Acta> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("diagnostico");
		assertThat(violation.getMessage()).isEqualTo("El diagnóstico tiene que tener un tamaño mínimo de 5 y máximo de 300 carácteres");
    }
    
    @Test
	void shouldNotValidateWithMMaxDiagnostico() {

		acta.setExploracion(generateRandom(78));
        acta.setDiagnostico(generateRandom(764));
        acta.setDescripcion(generateRandom(23));

		Validator validator = createValidator();
		Set<ConstraintViolation<Acta>> constraintViolations = validator.validate(acta);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Acta> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("diagnostico");
		assertThat(violation.getMessage()).isEqualTo("El diagnóstico tiene que tener un tamaño mínimo de 5 y máximo de 300 carácteres");
	}

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
    