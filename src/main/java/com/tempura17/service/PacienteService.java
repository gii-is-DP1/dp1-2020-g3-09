package com.tempura17.service;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import com.tempura17.model.Paciente;
import com.tempura17.repository.PacienteRepository;
import org.springframework.stereotype.Service;



@Service
public class PacienteService {

    PacienteRepository pacienteRepository;
    
    @Autowired
    public PacienteService(PacienteRepository pacienteRepository){
      this.pacienteRepository = pacienteRepository;
    }
    
    public Collection<Paciente> findAll(){
      return pacienteRepository.findAll();
    }
    
    public Optional<Paciente> findById(Integer id){
      return pacienteRepository.findById(id);
    }

    public void save(@Valid Paciente paciente){
      pacienteRepository.save(paciente);
    }

    @Transactional
    public void deleteById(Integer id){
      pacienteRepository.deleteById(id);
    }
	
    
}
