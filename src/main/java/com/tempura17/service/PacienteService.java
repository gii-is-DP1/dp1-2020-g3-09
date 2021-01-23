package com.tempura17.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.dao.DataAccessException;

import com.tempura17.model.CalculadoraSalud;
import com.tempura17.model.Paciente;
import com.tempura17.repository.CalculadoraRepository;
import com.tempura17.repository.PacienteRepository;
import org.springframework.stereotype.Service;


@Service
public class PacienteService {

    PacienteRepository pacienteRepository;

    CalculadoraRepository calculadoraRepository;

    UserService userService;

    AuthoritiesService authoritiesService;
    
    @Autowired
    public PacienteService(PacienteRepository pacienteRepository,CalculadoraRepository calculadoraRepository,UserService userService,AuthoritiesService authoritiesService){
      this.pacienteRepository = pacienteRepository;
      this.calculadoraRepository = calculadoraRepository;
      this.userService = userService;
      this.authoritiesService = authoritiesService;
    }
    
    @Transactional(readOnly = true)
    public Collection<Paciente> findAll(){
      return pacienteRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Optional<Paciente> findById(Integer id){
      return pacienteRepository.findById(id);
    }

    @Transactional
    public void save(@Valid Paciente paciente){
      pacienteRepository.save(paciente);
    }

    @Transactional
    public void deleteById(Integer id){
      pacienteRepository.deleteById(id);
    }

    @Transactional
    public CalculadoraSalud findCalculadoraByPacienteId(int pacienteId){
      return calculadoraRepository.findByPacienteId(pacienteId);
    }


    @Transactional
	  public void savePaciente(Paciente paciente) throws DataAccessException {
		pacienteRepository.save(paciente);		
		userService.saveUser(paciente.getUser());
		authoritiesService.saveAuthorities(paciente.getUser().getUsername(), "paciente");
	}		
	
    
}
