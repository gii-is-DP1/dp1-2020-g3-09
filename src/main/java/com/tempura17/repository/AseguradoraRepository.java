package com.tempura17.repository;

import org.springframework.data.repository.CrudRepository;
import java.util.Collection;
import java.util.Optional;

import com.tempura17.model.Aseguradora;

public interface AseguradoraRepository extends CrudRepository<Aseguradora, Integer> {
    
    Collection<Aseguradora> findAll();
  
    Optional<Aseguradora> findById(Integer id);
}
