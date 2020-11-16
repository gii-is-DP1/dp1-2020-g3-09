package com.tempura17.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import com.tempura17.model.Cita;

public interface CitaRepository extends CrudRepository<Cita, Long> {
    
    Collection<Cita> findAll();

    Cita findById(long id);
    
}
