package com.tempura17.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tempura17.model.Alarma;

@Repository
public interface AlarmaRepository extends CrudRepository<Alarma, Long>{
	
	@Query("SELECT a FROM Alarma a WHERE a.cita.id =  :citaId")
	Alarma findByCitaId(@Param("citaId")int citaId);
	
    Collection<Alarma> findAll();
    
    Optional<Alarma> findById(int id);
	
}
