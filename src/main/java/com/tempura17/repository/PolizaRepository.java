package com.tempura17.repository;

import java.util.Collection;

import com.tempura17.model.Poliza;

import org.springframework.data.repository.CrudRepository;

public interface PolizaRepository extends CrudRepository<Poliza, Integer> {

    Collection<Poliza> findAll();
    
}
