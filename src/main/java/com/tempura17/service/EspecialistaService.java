package com.tempura17.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import com.tempura17.model.Especialista;
import com.tempura17.model.Cita;
import com.tempura17.repository.EspecialistaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class EspecialistaService {

    private EspecialistaRepository especialistaRepository;
    private CitaService citaService;

    @Autowired
    public EspecialistaService(EspecialistaRepository especialistaRepository, CitaService citaService){
        this.especialistaRepository = especialistaRepository;  
        this.citaService = citaService;              
    }

    @Transactional(readOnly = true)
    public Collection<Especialista> findAll(){
        return especialistaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Especialista> findById(Integer id){
        return especialistaRepository.findById(id);
    }

    @Transactional
    public void save(@Valid Especialista especialista){
        this.especialistaRepository.save(especialista);
    }

    @Transactional
    public void deleteById(Integer id){
        this.especialistaRepository.deleteById(id);
    }

    @Transactional
    public void deleteCita(Integer citaId){
        Cita cita = citaService.findById(citaId).get();
        Especialista especialista = cita.getEspecialista();
        especialista.removeCita(cita);
        cita.setEspecialista(null);
        
        citaService.save(cita);
        especialistaRepository.save(especialista);

    }

}
