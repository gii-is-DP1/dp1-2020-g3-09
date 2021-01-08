package com.tempura17.service;

import java.util.Collection;
import java.util.Optional;
import com.tempura17.model.Acta;

import com.tempura17.repository.ActaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActaService {


    ActaRepository actaRepository;

    @Autowired
    public ActaService(ActaRepository actaRepository){
        this.actaRepository = actaRepository;
    }
    
    public Collection<Acta> findAll(){
        return actaRepository.findAll();
      }
      
    public Optional<Acta> findById(Integer id){
        return actaRepository.findById(id);
    }
}
