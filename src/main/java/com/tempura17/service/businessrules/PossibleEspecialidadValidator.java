package com.tempura17.service.businessrules;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

import com.tempura17.model.Cita;
import com.tempura17.model.Especialidad;
import com.tempura17.model.Formato;

public class PossibleEspecialidadValidator implements ConstraintValidator<ValidatePossibleEspecialidad, Cita>{


	@Override
	public boolean isValid(Cita cita, ConstraintValidatorContext context) {
		List<Especialidad> especialidades_online = new ArrayList<>();
		especialidades_online.add(Especialidad.PSIQUIATRIA);
		Formato formato = cita.getFormato();
		Especialidad especialidad = cita.getEspecialidad();
		return formato == Formato.ONLINE ? especialidades_online.contains(especialidad) : true;
	}

}
