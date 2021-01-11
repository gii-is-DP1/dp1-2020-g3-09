package com.tempura17.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import com.tempura17.model.Tratamiento;

import com.tempura17.repository.TratamientoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TratamientoService {


    TratamientoRepository tratamientoRepository;

    @Autowired
    public TratamientoService(TratamientoRepository tratamientoRepository){
        this.tratamientoRepository = tratamientoRepository;
    }
    
    @Transactional(readOnly = true)
    public Collection<Tratamiento> findAll(){
        return tratamientoRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Optional<Tratamiento> findById(Integer id){
        return tratamientoRepository.findById(id);
    }

    @Transactional
    public void save(@Valid Tratamiento tratamiento){
        tratamientoRepository.save(tratamiento);
    }
    
    @Transactional
    public void deleteById(Integer id){
        tratamientoRepository.deleteById(id);
    }

}
