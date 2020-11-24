package com.tempura17.repository;

import java.util.Collection;
import java.util.List;
import com.tempura17.model.*;

import org.springframework.data.repository.CrudRepository;

public interface PacienteRepository extends CrudRepository<Paciente, Long> {

  Collection<Paciente> findAll();
  
  Paciente findById(long id);
}