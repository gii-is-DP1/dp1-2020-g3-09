package com.tempura17.repository;

import java.util.Collection;
import java.util.Optional;

import com.tempura17.model.*;

import org.springframework.data.repository.CrudRepository;

public interface TratamientoRepository extends CrudRepository<Tratamiento, Integer> {

    Collection<Tratamiento> findAll();
    
    Optional<Tratamiento> findById(Integer id);
}
