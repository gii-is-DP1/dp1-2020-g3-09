package com.tempura17.repository;

import java.util.Collection;
import com.tempura17.model.Historial;

import org.springframework.data.repository.CrudRepository;

public interface HistorialRepository extends CrudRepository<Historial, Integer> {
    
    Collection<Historial> findAll();
    
}
