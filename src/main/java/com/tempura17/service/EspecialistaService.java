package com.tempura17.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import com.tempura17.model.Especialista;
import com.tempura17.repository.EspecialistaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class EspecialistaService {

    private EspecialistaRepository especialistaRepository;

    @Autowired
    public EspecialistaService(EspecialistaRepository especialistaRepository){
        this.especialistaRepository = especialistaRepository;                 
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

}
