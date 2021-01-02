package com.tempura17.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Collection;
import java.util.Optional;

import com.tempura17.model.Justificante;

public interface JustificanteRepository extends CrudRepository<Justificante, Integer>{

    Collection<Justificante> findAll();

    @Query("SELECT j FROM Justificante j WHERE j.cita.id = :citaId")
    Justificante findJustificanteByCitaId(@Param("citaId")int citaId);

    Optional<Justificante> findById(Integer id);
    
}
