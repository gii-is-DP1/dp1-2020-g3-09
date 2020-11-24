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
    CitaRepository citaRepository;

    public CitaService(){}

    public CitaService(CitaRepository citaRepository){
      this.citaRepository = citaRepository;
    }


    //Aggregate root
    public Collection<Cita> findAll(){
      return citaRepository.findAll();
    }

    public Optional<Cita> findById(Integer id){
      return citaRepository.findById(id);
    }

    public Collection<Cita> findByPacienteId(int pacienteId){
      return citaRepository.findByPacienteId(pacienteId);
    }
    
    public void save(@Valid Cita cita){
      citaRepository.save(cita);
    }

    public void delete(Cita cita){
      citaRepository.deleteById(cita.getId());
    }

	
    
}
