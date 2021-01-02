package com.tempura17.service;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import com.tempura17.model.CalculadoraSalud;
import com.tempura17.model.Paciente;
import com.tempura17.repository.CalculadoraRepository;
import com.tempura17.repository.PacienteRepository;
import org.springframework.stereotype.Service;
import com.tempura17.model.Cita;



@Service
public class PacienteService {

    PacienteRepository pacienteRepository;

    CalculadoraRepository calculadoraRepository;
    
    @Autowired
    public PacienteService(PacienteRepository pacienteRepository,CalculadoraRepository calculadoraRepository){
      this.pacienteRepository = pacienteRepository;
      this.calculadoraRepository = calculadoraRepository;
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

    public void saveCitaForPaciente(Integer id, Cita cita){
      pacienteRepository.findById(id).get().getCitas().add(cita);
    }

    @Transactional
    public void deleteById(Integer id){
      pacienteRepository.deleteById(id);
    }

    public CalculadoraSalud findCalculadoraByPacienteId(int pacienteId){
      return calculadoraRepository.findByPacienteId(pacienteId);
    }
	
    
}
