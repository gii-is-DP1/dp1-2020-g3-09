package com.tempura17.web.api;

import java.util.Collection;

import com.tempura17.service.PacienteService;
import com.tempura17.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PacienteREST{

	@Autowired
	private final PacienteService pacienteService;
    
    public PacienteREST(PacienteService pacienteService){
        this.pacienteService = pacienteService;
    }

    
	@GetMapping("/api/pacientes")
	public Collection<Paciente> findAll() {
		return pacienteService.findAll();
	}
	
}