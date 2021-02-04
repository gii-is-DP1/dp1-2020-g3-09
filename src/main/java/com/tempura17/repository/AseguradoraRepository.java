package com.tempura17.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

import com.tempura17.model.Aseguradora;

public interface AseguradoraRepository extends CrudRepository<Aseguradora, Integer> {
    
    Collection<Aseguradora> findAll();

    @Query("SELECT a FROM Aseguradora a WHERE a.nombre = :nombre")
    Aseguradora findByNombreAseguradora(@Param("nombre")String nombre);
  
    Optional<Aseguradora> findById(Integer id);
}
