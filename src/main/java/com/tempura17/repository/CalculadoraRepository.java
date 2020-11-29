package com.tempura17.repository;

import java.util.Collection;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.tempura17.model.CalculadoraSalud;

public interface CalculadoraRepository extends CrudRepository<CalculadoraSalud,Long>{

    Collection<CalculadoraSalud> findAll();

    CalculadoraSalud findById(long id);

    @Query("SELECT calc FROM CalculadoraSalud calc WHERE calc.paciente.id = :pacienteId")
    CalculadoraSalud findByPacienteId(@Param("pacienteId")int pacienteId);

}