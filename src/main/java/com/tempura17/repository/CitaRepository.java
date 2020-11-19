package com.tempura17.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tempura17.model.Cita;

public interface CitaRepository extends CrudRepository<Cita, Long> {
    
    Collection<Cita> findAll();

    Cita findById(long id);

    @Query("SELECT c FROM Cita c WHERE c.paciente.id = :pacienteId")
    Collection<Cita> findByPacienteId(@Param("pacienteId")int pacienteId);
    
}
