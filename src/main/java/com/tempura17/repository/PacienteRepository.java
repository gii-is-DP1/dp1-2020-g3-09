package com.tempura17.repository;

import java.util.Collection;
import java.util.Optional;

import com.tempura17.model.*;

import org.springframework.data.repository.CrudRepository;

public interface PacienteRepository extends CrudRepository<Paciente, Integer> {

  Collection<Paciente> findAll();
  
  Optional<Paciente> findById(Integer id);

  // Pendiente de inclusion
  //Paciente findByCitaId(Integer citaId);

}