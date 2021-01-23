package com.tempura17.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.tempura17.model.CalculadoraSalud;

public interface CalculadoraRepository extends CrudRepository<CalculadoraSalud, Integer> {

    Collection<CalculadoraSalud> findAll();

    Optional<CalculadoraSalud> findById(Integer id);

    @Query("SELECT calc FROM CalculadoraSalud calc WHERE calc.paciente.id = :pacienteId")
    CalculadoraSalud findByPacienteId(@Param("pacienteId")int pacienteId);


    @Query("SELECT calculadora FROM CalculadoraSalud calculadora WHERE calculadora.id =:id")
	public CalculadoraSalud findById(@Param("id") int id);

}