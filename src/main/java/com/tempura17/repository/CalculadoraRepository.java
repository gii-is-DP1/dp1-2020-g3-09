package com.tempura17.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import com.tempura17.model.CalculadoraSalud;

public interface CalculadoraRepository extends CrudRepository<CalculadoraSalud,Long>{

    Collection<CalculadoraSalud> findAll();

    CalculadoraSalud findById(long id);

}