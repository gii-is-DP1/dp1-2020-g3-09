package com.tempura17.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

  public Collection<CalculadoraSalud> findAll() {
    return calculadoraRepository.findAll();
  }

  public Optional<CalculadoraSalud> findById(int id) {
        return calculadoraRepository.findById(id);
    }

    public void save(@Valid CalculadoraSalud calculadora){
        calculadoraRepository.save(calculadora);
    }

    public CalculadoraSalud findByPacienteId(int pacienteId){
        return calculadoraRepository.findByPacienteId(pacienteId);
      }
	
    
}
