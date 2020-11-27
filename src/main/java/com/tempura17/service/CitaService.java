package com.tempura17.service;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.tempura17.model.Cita;
import com.tempura17.model.Paciente;
import com.tempura17.repository.CitaRepository;
import com.tempura17.repository.PacienteRepository;

import org.springframework.stereotype.Service;



@Service
public class CitaService {

<<<<<<< HEAD
    @Autowired
    CitaRepository repo;


    public void delete(Cita cita){
      repo.deleteById(cita.getId());
    }

    public void save(@Valid Cita cita){
      repo.save(cita);
    }
=======
    private CitaRepository citaRepository;
>>>>>>> master
    
    private PacienteRepository pacienteRepository;

    @Autowired
    public CitaService(CitaRepository citaRepository, PacienteRepository pacienteRepository){
      super();
      this.citaRepository = citaRepository;
      this.pacienteRepository = pacienteRepository;
    }


    //Aggregate root
    public Collection<Cita> findAll(){
      return citaRepository.findAll();
    }
<<<<<<< HEAD
    
    public Optional<Cita> findById(Integer id){
        return repo.findById(id);
    }

    public Collection<Cita> findByPacienteId(int pacienteId){
      return repo.findByPacienteId(pacienteId);
    }
=======

    public Optional<Cita> findById(Integer id){
      return citaRepository.findById(id);
    }

    public Collection<Cita> findByPacienteId(int pacienteId){
      return citaRepository.findByPacienteId(pacienteId);
    }

    /*Necesita mas investigacion.
    public Paciente findPacienteByCitaId(int citaId) {
      return pacienteRepository.findByCitaId(citaId);
    }
    */
    public void save(@Valid Cita cita){
      citaRepository.save(cita);
    }

    @Transactional
    public void savePaciente(Paciente paciente) throws DataAccessException {
      pacienteRepository.save(paciente);
    }

    public void delete(Cita cita){
      citaRepository.deleteById(cita.getId());
    }

>>>>>>> master
	
    
}
