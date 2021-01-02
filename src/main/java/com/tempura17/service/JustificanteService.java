package com.tempura17.service;

import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.tempura17.model.Justificante;
import com.tempura17.repository.JustificanteRepository;



@Service
public class JustificanteService {

    private JustificanteRepository justificanteRepository;

    @Autowired
    public JustificanteService(JustificanteRepository justificanteRepository){
        this.justificanteRepository = justificanteRepository;
    }


    public Collection<Justificante> findAll(){
        return justificanteRepository.findAll();
    }
    
    public Justificante findJustificanteByCitaId(int citaId){
        return justificanteRepository.findJustificanteByCitaId(citaId);
    }

    public Optional<Justificante> findById(Integer id){
        return justificanteRepository.findById(id);
    }

    public void save(@Valid Justificante justificante){
        justificanteRepository.save(justificante);
      }
  
      
    
}
