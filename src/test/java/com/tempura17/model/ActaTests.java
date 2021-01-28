package com.tempura17.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ActaTests {

    private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
    }


    @Test
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

		LocaleContextHolder.setLocale(Locale.ENGLISH);
        Acta acta = new Acta();
        Especialista especialista = new Especialista();
        acta.setEspecialista(especialista);
        Cita cita = new Cita();
        acta.setCita(cita);
        acta.setDescripcion("hola");

		Validator validator = createValidator();
		Set<ConstraintViolation<Acta>> constraintViolations = validator.validate(acta);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Acta> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("descripcion");
		assertThat(violation.getMessage()).isEqualTo("La descripción tiene que tener un tamaño mínimo de 5 y máximo de 300 carácteres");
    }
    
    @Test
	void shouldNotValidateWithMMaxDescripcion() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
        Acta acta = new Acta();
        Especialista especialista = new Especialista();
        acta.setEspecialista(especialista);
        Cita cita = new Cita();
        acta.setCita(cita);
        acta.setDescripcion("dfijsdofhsdpoiufhasioudfhiouasdfhiouashdfiuoasdfhuioashdfiouashdfouiashdfiu"+
        "oasdhdfijasdfiupbfuo8wrfbspd9fubaspidfbaspoñdufbpasdpofuihasdpsjoajfhuasifnpasuoifdhsilkdnflaskñdfnaksl"+
        "dfhlkñasdfnasoiñdfjasñdofynworñfhlksanfklasdñjf nasĺdpfj aslñkdfjoiasrjtiovañsdhnpfrsaipertvjanskldfjiasodfmna dsvidojfiodf");

		Validator validator = createValidator();
		Set<ConstraintViolation<Acta>> constraintViolations = validator.validate(acta);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Acta> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("descripcion");
		assertThat(violation.getMessage()).isEqualTo("La descripción tiene que tener un tamaño mínimo de 5 y máximo de 300 carácteres");
	}
}
    