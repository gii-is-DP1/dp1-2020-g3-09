package com.tempura17.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

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
	void shouldNotValidateWithNullPoliza() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
        Tratamiento tratamiento = new Tratamiento();
        Acta acta = new Acta();
        tratamiento.setActa(acta);
        tratamiento.setPoliza(null);

		Validator validator = createValidator();
		Set<ConstraintViolation<Tratamiento>> constraintViolations = validator.validate(tratamiento);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Tratamiento> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("poliza");
		assertThat(violation.getMessage()).isEqualTo("La poliza no puede ser nula");
    }
    
    @Test
	void shouldNotValidateWithNullActa() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
        Tratamiento tratamiento = new Tratamiento();

        tratamiento.setActa(null);
        Poliza poliza = new Poliza();
        tratamiento.setPoliza(poliza);

		Validator validator = createValidator();
		Set<ConstraintViolation<Tratamiento>> constraintViolations = validator.validate(tratamiento);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Tratamiento> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("acta");
		assertThat(violation.getMessage()).isEqualTo("El acta no puede ser nulo");
    }
    
    @Test
	void shouldNotValidateWithNoDuracion() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
        Tratamiento tratamiento = new Tratamiento();

        Acta acta = new Acta();
        tratamiento.setActa(acta);
        Poliza poliza = new Poliza();
        tratamiento.setPoliza(poliza);
        tratamiento.setDuracion(0);

		Validator validator = createValidator();
		Set<ConstraintViolation<Tratamiento>> constraintViolations = validator.validate(tratamiento);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Tratamiento> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("duracion");
		assertThat(violation.getMessage()).isEqualTo("la duración no puede ser menor de 1 día");
    }
    
    @Test
	void shouldNotValidateWithMinDescripcion() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
        Tratamiento tratamiento = new Tratamiento();

        Acta acta = new Acta();
        tratamiento.setActa(acta);
        Poliza poliza = new Poliza();
        tratamiento.setPoliza(poliza);
        tratamiento.setDuracion(1);
        tratamiento.setDescripcion("1");

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

        Acta acta = new Acta();
        tratamiento.setActa(acta);
        Poliza poliza = new Poliza();
        tratamiento.setPoliza(poliza);
        tratamiento.setDuracion(1);
        tratamiento.setDescripcion("dfijsdofhsdpoiufhasioudfhiouasdfhiouashdfiuoasdfhuioashdfiouashdfouiashdfiu"+
        "oasdhdfijasdfiupbfuo8wrfbspd9fubaspidfbaspoñdufbpasdpofuihasdpsjoajfhuasifnpasuoifdhsilkdnflaskñdfnaksl"+
        "dfhlkñasdfnasoiñdfjasñdofynworñfhlksanfklasdñjf nasĺdpfj aslñkdfjoiasrjtiovañsdhnpfrsaipertvjanskldfjiasodfmna dsvidojfiodf");

		Validator validator = createValidator();
		Set<ConstraintViolation<Tratamiento>> constraintViolations = validator.validate(tratamiento);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Tratamiento> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("descripcion");
		assertThat(violation.getMessage()).isEqualTo("La descripción tiene que tener un tamaño mínimo de 10 y máximo de 300 carácteres");
	}
    
}
