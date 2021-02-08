package com.tempura17.service.businessrules;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import com.tempura17.model.Acta;

public class PossibleActaValidator implements ConstraintValidator<ValidatePossibleActa, Acta>{


	@Override
	public boolean isValid(Acta acta, ConstraintValidatorContext context) {
        String exploracion = acta.getExploracion();
        String diagnostico = acta.getDiagnostico();

        return exploracion.equalsIgnoreCase(diagnostico) ? false : true;
	}



}
