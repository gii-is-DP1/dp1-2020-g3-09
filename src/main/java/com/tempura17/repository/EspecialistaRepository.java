package com.tempura17.repository;

import java.util.Collection;

import com.tempura17.model.Especialista;

import org.springframework.data.repository.CrudRepository;

public interface EspecialistaRepository extends CrudRepository<Especialista, Integer>{
    

    public Collection<Especialista> findAll();
}
