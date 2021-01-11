package com.tempura17.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.tempura17.model.CalculadoraSalud;
import com.tempura17.repository.CalculadoraRepository;
import org.springframework.stereotype.Service;

@Service
public class CalculadoraService {

  private CalculadoraRepository calculadoraRepository;

  @Autowired
  public CalculadoraService(CalculadoraRepository calculadoraRepository) {
    super();
    this.calculadoraRepository = calculadoraRepository;
  }

  @Transactional(readOnly = true)
  public Collection<CalculadoraSalud> findAll() {
    return calculadoraRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Optional<CalculadoraSalud> findById(int id) {
        return calculadoraRepository.findById(id);
  }

  @Transactional(readOnly = true)
  public CalculadoraSalud findByPacienteId(int pacienteId){
    return calculadoraRepository.findByPacienteId(pacienteId);
  }

  @Transactional
  public void save(@Valid CalculadoraSalud calculadora){
      calculadoraRepository.save(calculadora);
  }

	
    
}
