package com.tempura17.repository;

import java.util.Collection;
import java.util.Optional;

import com.tempura17.model.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TratamientoRepository extends CrudRepository<Tratamiento, Integer> {

    Collection<Tratamiento> findAll();

    @Query("SELECT t FROM Tratamiento t WHERE t.acta.id = :actaId")
    Collection<Tratamiento> findByActaId(@Param("actaId")int actaId);
    
    Optional<Tratamiento> findById(Integer id);
}
