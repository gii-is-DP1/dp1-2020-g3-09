package com.tempura17.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import com.tempura17.model.Cita;
import com.tempura17.repository.CitaRepository;
import org.springframework.stereotype.Service;



@Service
public class CitaService {

    @Autowired
    CitaRepository repo;
    
    public Collection<Cita> findAll(){
		return repo.findAll();
    }
    
    public Cita findById(long id){
        return repo.findById(id);
    }

    public Collection<Cita> findByPacienteId(int pacienteId){
      return repo.findByPacienteId(pacienteId);
    }
	
    
}
