package com.tempura17.service;

import java.util.Collection;
import java.util.Optional;

import com.tempura17.model.Especialista;
import com.tempura17.repository.EspecialistaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EspecialistaService {

    @Autowired
    EspecialistaRepository especialistaRepository;

    public EspecialistaService(){}

    public EspecialistaService(EspecialistaRepository especialistaRepository){
        this.especialistaRepository = especialistaRepository;
    }

    public Collection<Especialista> findAll(){
        return especialistaRepository.findAll();
    }


    public Optional<Especialista> findById(Integer id){
        return especialistaRepository.findById(id);
    }
    
}
