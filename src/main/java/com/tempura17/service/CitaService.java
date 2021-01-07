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

    private CitaRepository citaRepository;
    
    private PacienteService pacienteService;

    @Autowired
    public CitaService(CitaRepository citaRepository, PacienteService pacienteService){
      super();
      this.citaRepository = citaRepository;
      this.pacienteService = pacienteService;
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

    /*Necesita mas investigacion.
    public Paciente findPacienteByCitaId(int citaId) {
      return pacienteRepository.findByCitaId(citaId);
    }
    */
    public void save(@Valid Cita cita){
      citaRepository.save(cita);
      Paciente paciente = cita.getPaciente();
      paciente.addCita(cita);
      this.pacienteService.save(paciente);
    }

    public void delete(Cita cita){
      citaRepository.deleteById(cita.getId());
    }

    public void deleteById(Integer id){
      citaRepository.deleteById(id);
    }

	
    
}
