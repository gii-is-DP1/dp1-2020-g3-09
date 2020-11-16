package com.tempura17.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import com.tempura17.model.Paciente;
import com.tempura17.repository.PacienteRepository;
import org.springframework.stereotype.Service;



@Service
public class PacienteService {

    @Autowired
    PacienteRepository repo;
    
    public Collection<Paciente> findAll(){
      
		return repo.findAll();
	}
	
    
}
