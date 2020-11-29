package com.tempura17.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import com.tempura17.model.CalculadoraSalud;
import com.tempura17.model.Paciente;
import com.tempura17.repository.CalculadoraRepository;
import com.tempura17.repository.PacienteRepository;
import org.springframework.stereotype.Service;



@Service
public class PacienteService {

    private PacienteRepository repo;

    private CalculadoraRepository calculadoraRepository;

    @Autowired
	  public PacienteService(PacienteRepository repo,
			CalculadoraRepository calculadoraRepository) {
		this.repo = repo;
		this.calculadoraRepository = calculadoraRepository;
	}
    
    public Collection<Paciente> findAll(){
    return repo.findAll();
    }
    
    public CalculadoraSalud findCalculadoraByPacienteId(int pacienteId){
      return calculadoraRepository.findByPacienteId(pacienteId);
    }

	
    
}
