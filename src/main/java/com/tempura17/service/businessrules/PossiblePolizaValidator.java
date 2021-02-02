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

import com.tempura17.model.Cita;
import com.tempura17.model.Especialidad;
import com.tempura17.model.Formato;
import com.tempura17.model.Cobertura;
import com.tempura17.model.Paciente;

public class PossiblePolizaValidator implements ConstraintValidator<ValidatePossiblePoliza, Cita>{


	@Override
	public boolean isValid(Cita cita, ConstraintValidatorContext context) {
			Paciente paciente = cita.getPaciente();
			Cobertura cobertura = paciente.getPoliza().getCobertura();
			Integer numCitas = paciente.getCitas().size();

			if(cobertura == null){
				return true;
			}else {
				if(cobertura == Cobertura.PARCIAL){
					return numCitas <= 5 ? true : false;
				}else if(cobertura == Cobertura.TOTAL){
					return numCitas <= 15 ? true : false;
				}else {
					return true; 
				}
			}
	}



}
