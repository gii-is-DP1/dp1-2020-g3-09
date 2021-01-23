package com.tempura17.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;
import org.springframework.transaction.annotation.Transactional;


import com.tempura17.model.Cita;
import com.tempura17.repository.CitaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CitaService {

    private CitaRepository citaRepository;
    

    @Autowired
    public CitaService(CitaRepository citaRepository){
      super();
      this.citaRepository = citaRepository;
    }


    //Aggregate root
    @Transactional(readOnly = true)
    public Collection<Cita> findAll(){
      return citaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Cita> findById(Integer id){
      return citaRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Collection<Cita> findByPacienteId(int pacienteId){
      return citaRepository.findByPacienteId(pacienteId);
    }

    @Transactional
    public void save(@Valid Cita cita){
      citaRepository.save(cita);
    }

    @Transactional
    public void delete(Cita cita){
      citaRepository.deleteById(cita.getId());
    }

    @Transactional
    public void deleteById(Integer id){
      citaRepository.deleteById(id);
    }

	
    
}
