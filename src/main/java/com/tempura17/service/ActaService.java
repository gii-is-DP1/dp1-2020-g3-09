package com.tempura17.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import com.tempura17.model.Acta;

import com.tempura17.repository.ActaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ActaService {


    ActaRepository actaRepository;

    @Autowired
    public ActaService(ActaRepository actaRepository){
        this.actaRepository = actaRepository;
    }
    
    @Transactional(readOnly = true)
    public Collection<Acta> findAll(){
        return actaRepository.findAll();
    }

    @Transactional(readOnly = true)  
    public Optional<Acta> findById(Integer id){
        return actaRepository.findById(id);
    }

    @Transactional
    public void save(@Valid Acta acta){
        actaRepository.save(acta);
    }
}
