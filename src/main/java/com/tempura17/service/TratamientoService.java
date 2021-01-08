package com.tempura17.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import com.tempura17.model.Tratamiento;

import com.tempura17.repository.TratamientoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TratamientoService {


    TratamientoRepository tratamientoRepository;

    @Autowired
    public TratamientoService(TratamientoRepository tratamientoRepository){
        this.tratamientoRepository = tratamientoRepository;
    }
    
    public Collection<Tratamiento> findAll(){
        return tratamientoRepository.findAll();
      }
      
    public Optional<Tratamiento> findById(Integer id){
        return tratamientoRepository.findById(id);
    }

    public void save(@Valid Tratamiento tratamiento){
        tratamientoRepository.save(tratamiento);
      }
}
