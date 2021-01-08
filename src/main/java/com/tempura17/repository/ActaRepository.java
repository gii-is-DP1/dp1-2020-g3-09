package com.tempura17.repository;

import java.util.Collection;
import java.util.Optional;

import com.tempura17.model.*;

import org.springframework.data.repository.CrudRepository;

public interface ActaRepository extends CrudRepository<Acta, Integer> {

    Collection<Acta> findAll();
    
    Optional<Acta> findById(Integer id);
}
